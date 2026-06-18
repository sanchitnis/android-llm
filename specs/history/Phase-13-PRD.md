# Product Requirements Document (PRD) - Admission Counselor AI

This document details the functional, non-functional, model-specific, and hardware baseline requirements for the standalone Android Admission Counselor App.

---

## 1. Introduction and Objectives

### 1.1 Purpose
The Admission Counselor App is a mobile-only, offline-first application designed to help prospective students research college admissions, courses, fees, deadlines, and eligibility criteria on their Android devices.

### 1.2 Core Objective
Deploy a locally executing large language model using the LiteRT-LM framework. The app must execute reasoning, response generation, and retrieval entirely on-device to protect student privacy, eliminate server costs, and function without cellular or Wi-Fi connectivity.

---

## 2. Core User Stories

| Role | User Story | Acceptance Criteria |
| :--- | :--- | :--- |
| Prospective Student | I want to ask questions about course details and tuition fees offline. | Responses are accurate, generated in under 3 seconds, and function without internet connectivity. |
| Applicant | I want to verify if my current grades meet the admission eligibility requirements for a program. | The system analyzes GPA/qualification inputs against structured university guidelines and provides clear pass/fail feedback. |
| High School Senior | I want to query critical application deadlines. | The system retrieves exact date-based constraints for the targeted term and prompts the student with active reminders. |

---

## 3. Hardware Baseline and Constraints

The model runtime footprint necessitates clear hardware stratification to prevent Out-Of-Memory (OOM) app crashes and maintain acceptable user experiences.

### 3.1 Device Specifications

| Parameter | Minimum Requirements | Recommended Profile |
| :--- | :--- | :--- |
| **Android OS** | Android 10 (API level 29) | Android 13 (API level 33) or higher |
| **System RAM** | 6 GB total system RAM | 8 GB or higher total system RAM |
| **Available Storage** | 4.0 GB free disk space (model + data) | 6.0 GB free disk space |
| **Hardware Accelerator** | Multicore CPU (ARM64) | GPU with Vulkan/OpenCL acceleration support |
| **LiteRT Backend** | XNNPACK CPU Delegate | ML Drift GPU Delegate |

---

## 4. Model and Runtime Specifications

The application uses a specific model and runtime framework tailored for mobile edge execution.

### 4.1 Model Characteristics
- **Model Target**: Exclusively the `gemma-4-E2B-it` model.
- **Model Footprint**: 2.58 GB on-disk storage.
- **File Format**: `.litertlm` (quantized format optimized for the LiteRT-LM engine).
- **Quantization Level**: 4-bit integer weights (with 8-bit activations where appropriate) to achieve the 2.58 GB footprint.
- **Context Window Budget**: 4096 tokens total (input prompt + retrieved context + generated response). The KV-cache RAM overhead for this window is approximately 200 MB, well within the 2 GB peak RAM ceiling.

### 4.2 Runtime Constraints
- **Inference Lifecycle**: Only one active inference session is allowed at any time. Dual-session allocation is blocked at the system level.
- **Background Execution**: All inference operations must run on a background thread. Running inference on the main UI thread is strictly forbidden.
- **Speculative Decoding**: Support for Multi-Token Prediction (MTP) utilizing a smaller drafter model if available, targeting a 1.5x throughput improvement.

---

## 5. Functional Requirements

### 5.1 On-Device Chat Interface
- The system must provide a conversational UI that displays responses incrementally (token streaming).
- The chat interface must expose simple feedback mechanisms (thumbs up/down) for response logging.

### 5.2 Context Retrieval (On-Device RAG)
- The app must store university admission guides, fees, and course lists in an on-device structured database (SQLite or Protocol Buffers).
- The system must retrieve relevant context matching the user query and inject it into the model context block before inference.

### 5.3 Lifetime and State Management
- The runtime engine must load the model into memory only when an active chat session starts.
- The model must be automatically unloaded from RAM after 5 minutes of idle time or when the app is placed in the background to free system memory.

---

## 6. Non-Functional Requirements

### 6.1 Latency and Throughput Budgets

| Execution Profile | Time to First Token (TTFT) | Generation Throughput |
| :--- | :--- | :--- |
| **GPU (ML Drift)** | Under 0.5 seconds | 15 tokens per second or higher |
| **CPU (XNNPACK)** | Under 1.5 seconds | 5 tokens per second or higher |

### 6.2 Application Budgets
- **App Package (APK) Size**: Under 150 MB (excluding the model asset).
- **Memory (RAM) Ceiling**: The app must not exceed a maximum RAM footprint of 2.0 GB during peak inference.
- **Thermal Performance**: Generation throughput must not throttle by more than 20% over a continuous 3 minute conversation.
