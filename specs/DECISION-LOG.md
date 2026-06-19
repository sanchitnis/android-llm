# Technical Decision Log - Admission Counselor AI

This document reviews the primary engineering and implementation decisions made for the local Admission Counselor Android application. It compares the selected options with viable alternatives, detailing pros, cons, and trade-offs.

**Document Date**: June 18, 2026

---

## 1. Model Packaging and Delivery Strategy

The app runs a 2.58 GB `gemma-4-E2B-it` model locally on the user's device. Packaging and delivering this file safely without violating performance or security guarantees is a core requirement.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: File Sideloading (External / Private Storage)** | The model weights file is side-loaded directly onto the device (either in the app's external files directory or the standard `Download/` folder) without packaging it inside the app bundle. | - Fully eliminates double storage overhead (takes exactly 2.58 GB, not 5.16 GB).<br>- Adheres strictly to the zero-network-permission guarantee (offline-only execution).<br>- Avoids complex Play Asset Delivery build setup, split modules, and Play Store API dependencies. | - User or installer must manually place the 2.58 GB model file in the designated folder on the device. |
| **Alternative A: Install-Time Play Asset Delivery (PAD)** | Model is packaged as split asset packs in the App Bundle with `deliveryMode = "install-time"`. Google Play handles delivery during app download. | - Fully eliminates post-install downloads.<br>- Automatic download resume. | - Exceeds individual asset pack size limits (1.5 GB), requiring build-time file splitting and on-device concatenation.<br>- Consumes double the disk storage (5.16 GB) on the device. |
| **Alternative B: Dynamic HTTPS CDN Download** | The app shell downloads the model from a custom Content Delivery Network (CDN) using a custom HTTP downloader on first launch. | - Keeps app download size small.<br>- No storage duplication. | - Violates the strict zero-network promise (requires declaring network permissions).<br>- Fragile custom download/resume logic. |

### Selected Option Rationale
**File Sideloading** was selected as the only delivery method. It enforces the zero-network-permission guarantee while completely avoiding the massive 5.16 GB storage duplication penalty and the code complexity associated with Play Asset Delivery (PAD). By placing the 2.58 GB model binary in the app's scoped external files directory (which does not require runtime permissions) or standard `Download/` folder, the app reads the model directly with zero duplication and zero network access.

---

## 2. On-Device Context Retrieval (RAG) Database

To answer specific questions about REVA University, the application retrieves relevant context chunks using a local database.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: SQLite FTS5 Virtual Tables + BM25 Ranking** | Course data is stored in standard relational SQLite tables. An FTS5 virtual table indexes full-text columns. Queries use the FTS5 `MATCH` syntax ranked by the `bm25()` function. | - Native to Android (no extra binary footprint).<br>- Extremely fast query speeds (sub-millisecond execution).<br>- Zero extra RAM overhead.<br>- Relational columns allow exact filtering (e.g. by program level, department, or duration). | - Lacks semantic understanding (relies on keyword matching and synonym expansion). |
| **Alternative A: On-Device Vector Database (e.g. ObjectBox Vector, Realm)** | Text chunks are embedded into vector arrays using a local embedding model. Similarity queries are run against a local vector database. | - Supports semantic search (e.g. searching "AI" retrieves "Machine Learning" content even without exact keyword matches). | - Requires running an on-device text embedding model, adding 100 to 150 MB of RAM usage.<br>- Generating embeddings for user queries adds 200 to 1000ms latency.<br>- Increases APK size by 15 to 25 MB for the embedding model binary and DB library. |
| **Alternative B: Standard SQL LIKE Queries** | Simple queries using `LIKE '%term%'` filters across text columns. | - Extremely simple to write and maintain. | - Very slow on large text blocks.<br>- No relevancy ranking (results are returned in arbitrary order).<br>- Fails to resolve queries with multiple terms or variations. |
| **Alternative C: In-Memory JSON search** | Load the entire JSON dataset into JVM memory on startup and filter using Kotlin collection functions. | - Zero database setup or schema migrations. | - Consumes precious JVM heap space.<br>- Slow matching performance on larger text blocks.<br>- Fails to support structured queries efficiently. |

### Selected Option Rationale
**SQLite FTS5 + BM25** was chosen because it delivers sub-millisecond retrieval speeds with zero additional RAM or binary size overhead. On-device vector databases require running a secondary embedding model, which is too heavy for mid-range mobile devices that are already running a 2.58 GB LLM. FTS5 provides the necessary keyword accuracy for factual admission queries (such as eligibility, fees, and deadlines) without the performance cost.

---

## 3. Local LLM Inference Engine Wrapper

The app must execute local inference using the model binary. Selecting the proper integration layer is crucial for performance and stability.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: MediaPipe Tasks GenAI API** | Google's official developer API built on LiteRT (TensorFlow Lite), specifically optimized for Gemma models. | - Official Google support and maintenance.<br>- Dynamic hardware acceleration (ML Drift GPU delegate / XNNPACK CPU delegate).<br>- Supports speculative decoding out-of-the-box.<br>- Native C++ execution ensures high speed. | - Lacks a native cancellation API (cancelling requires closing and re-instantiating the model).<br>- Limited low-level tuning options compared to raw compiler bindings. |
| **Alternative A: Custom llama.cpp JNI Wrapper** | Compile llama.cpp using the Android NDK and write JNI wrappers to interact with the engine. | - Full low-level control over execution parameters and context.<br>- Supports native token-level cancellation without reloading the model. | - Extremely high engineering and maintenance overhead.<br>- Must compile and test native binaries (`.so` files) for multiple architectures (arm64-v8a, armeabi-v7a).<br>- No official vendor integration support. |
| **Alternative B: ONNX Runtime Mobile** | Convert the Gemma model to ONNX format and run it using the ONNX Runtime Mobile library. | - Cross-platform compatibility (code can be reused on iOS). | - Slower execution speeds on Android since Gemma-specific kernel optimizations are not as mature as MediaPipe's. |

### Selected Option Rationale
**MediaPipe Tasks GenAI** was chosen because it provides the most optimized execution kernels for Gemma models on Android. It handles GPU delegation and CPU thread allocation out-of-the-box, ensuring high throughput. The cancellation limitation is handled by a custom wrapper in the implementation.

---

## 4. Concurrency Control and Thread Safety

The LiteRT-LM C++ runtime is not thread-safe. Overlapping inference operations will crash the process or cause Out-of-Memory (OOM) failures.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: Dedicated Dispatcher + Mutex tryLock Busy Rejection** | Runs all engine lifecycle and inference tasks on a single-thread background dispatcher. A Kotlin `Mutex` guards the engine. If a new request comes in while the engine is busy, `tryLock()` fails and throws an exception immediately. | - Guarantees thread safety for the non-thread-safe C++ runtime.<br>- Immediately notifies the user of the busy state without freezing the UI.<br>- Prevents multiple model loads or concurrent runs, protecting the device from memory crashes. | - User cannot queue queries (though this matches the standard conversational model). |
| **Alternative A: Queued Channel Execution** | Incoming queries are placed in a queue and processed sequentially. | - No queries are dropped. | - In a conversational chat, queueing old queries is bad UX. If the user sends a new message, they expect the engine to focus on that message, not queue it behind previous ones. |
| **Alternative B: Bound Android Service with IPC** | Run the LLM engine in a separate, isolated Android Service process. Inter-Process Communication (IPC) is handled via Messenger or AIDL. | - If the LLM engine crashes, the main UI process remains alive.<br>- Android OS treats the background service as a separate memory pool. | - Significant performance overhead due to IPC data serialization.<br>- Adds 50 to 80 MB of system RAM overhead just to run the second process.<br>- Greatly increases architecture complexity. |

### Selected Option Rationale
A **Single-Thread Dispatcher with Mutex tryLock** was chosen because it keeps the application lightweight and thread-safe. Running the model in a separate process adds unnecessary IPC complexity and memory overhead. Using `tryLock()` prevents thread queue buildup and ensures the app responds immediately when the engine is busy.

---

## 5. Memory Lifecycle and Model Unloading Policy

The 2.58 GB model footprint requires aggressive memory management to avoid background termination by the Android Low Memory Killer (LMK).

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: 5-Min Idle Timeout + 30s Background Unload + OS LOMEM Callback** | Model is loaded lazily on the first query. It is kept in RAM during active chat. It is unloaded if:<br>- Idle for 5 minutes.<br>- App is in background for more than 30 seconds.<br>- System sends `TRIM_MEMORY_RUNNING_CRITICAL`. | - Minimizes long-term RAM footprint.<br>- Graceful 30-second background delay prevents reload lags during transient events like screen rotation.<br>- Responsive to OS memory pressure signals, preventing OOM crashes. | - First load after an idle period or background return takes 2 to 5 seconds. |
| **Alternative A: Load/Unload on Every Query** | The model is loaded before generating a response and closed immediately after the final token is delivered. | - The app uses almost zero RAM when the user is not actively typing. | - Unacceptable user experience. Every single message has a 2 to 5 second loading delay before generation starts. |
| **Alternative B: Keep Loaded Permanently** | Model is loaded on app launch and kept in memory until the process is terminated. | - Zero reload delays during the entire app session. | - The Android OS will immediately kill the app process as soon as it goes to the background to reclaim the 2.58 GB of memory, resulting in lost state. |

### Selected Option Rationale
The **Dynamic 5-Minute Idle + 30s Background Unload** strategy balances user experience and resource consumption. It prevents the app from being killed in the background while avoiding the reload delays of loading the model on every single turn.

---

## 6. Data Encryption and Privacy Safeguards

The application must protect student data and university resources while running offline.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: Android File-Based Encryption (FBE) + Sandbox Isolation** | Data at rest is secured via Android's native File-Based Encryption. The database is stored in the app's private data sandbox. | - No performance overhead on queries.<br>- No package size overhead.<br>- Simplified code structure using Room's native APIs. | - Rooted devices can access the raw database file (though the database contains only public university information). |
| **Alternative A: SQLCipher Database-Level Encryption** | The SQLite database file is encrypted at rest using the SQLCipher library. | - Protects data even if the device is rooted. | - Increases APK size by ~10 MB due to custom native binaries.<br>- Adds 5 to 15% latency overhead on every database query due to page-level decryption. |

### Selected Option Rationale
**File-Based Encryption + Sandbox Isolation** was chosen because the admission database contains only publicly available university information (fees, eligibility, and program descriptions). Adding SQLCipher adds package size and query latency without mitigating a real security threat. The student's private chat history is stored strictly in-memory and wiped on unload, protecting personal privacy.

---

## 7. Model Asset Packaging and Google Play Size Limits

Google Play limits individual asset packs to **1.5 GB** and the total of all install-time asset packs combined to **1.0 GB** (extendable to **4.0 GB** via Google Play Large Asset Packs approval). Our target `gemma-4-E2B-it` model file is **2.58 GB**, meaning a single asset pack file will fail Google Play Store validation.

| Option | Description | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Selected: File Sideloading (External / Private Storage)** | Place the single 2.58 GB `gemma-4-E2B-it.litertlm` file directly into private or scoped external storage folders. | - Keeps zero-network-permission guarantee.<br>- Eliminates 1.5 GB asset pack splitting/merge logic.<br>- Zero storage duplication overhead (only consumes 2.58 GB on the device). | - Sideloading is manual and requires side-distribution channels for the model weights file. |
| **Alternative A: Split Install-Time Asset Packs** | Split the model into two packs (:model-asset-pack-part1, :model-asset-pack-part2) under the 1.5 GB limit and merge them on first launch. | - Automatic download from Play Store. | - Double storage penalty (5.16 GB consumed on-device).<br>- Merge overhead on first run. |
| **Alternative B: Switch to a Smaller Model Target (<1.5 GB)** | Use a smaller model that fits in a single standard asset pack without splitting or sideloading. | - Simple delivery and compact storage. | - Reduced reasoning capability for complex admissions queries. |

### Selected Option Rationale
**File Sideloading** is selected as the exclusive delivery method. It avoids Google Play's 1.5 GB individual asset pack limits entirely, bypasses the 5.16 GB storage duplication penalty, and simplifies the codebase by removing all Play Core asset delivery libraries and dynamic split modules.


