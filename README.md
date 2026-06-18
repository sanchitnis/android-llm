# Android Admission Counselor AI

Welcome to the repository for the **Android Admission Counselor App**. This repository contains the complete, implementation-ready specifications and architecture for a standalone Android application that runs local LLM inference using **LiteRT-LM** with the **gemma-4-E2B-it** model (2.58 GB) entirely on-device.

## Project Status

Currently, this repository houses the **implementation-ready specifications** generated via a loop-engineering workflow. The actual implementation code will be built based on these specifications.

## Overview

The primary goal of this project is to provide a local AI agent that can assist with university admission queries, course details, fees, and deadlines, without any dependency on external API servers or cloud processing. All knowledge base retrievals and language model generation occur locally on the user's Android device.

### Key Features
- **Standalone Architecture**: Single Android application wrapper with no external API or IPC overhead.
- **Local Inference**: Uses the `gemma-4-E2B-it` model on-device via LiteRT-LM.
- **Context Injection**: Uses on-device RAG (Retrieval-Augmented Generation) databases to serve as the Counselor Knowledge Base.
- **Performance Optimized**: Implements dedicated background thread inference with strict concurrency controls and resource policies to prevent thermal throttling and OOM errors.

## Documentation and Specifications

All project specifications, architectural designs, runtime policies, and loop-engineering logs are documented in the `specs/` directory.

### Quick Links
*   [Specifications Overview (`specs/README.md`)](specs/README.md)
*   [Product Requirements Document (`specs/PRD.md`)](specs/PRD.md)
*   [Architecture (`specs/ARCHITECTURE.md`)](specs/ARCHITECTURE.md)
*   [App Design (`specs/APP-DESIGN.md`)](specs/APP-DESIGN.md)
*   [Test & Verification Plan (`specs/TEST-PLAN.md`)](specs/TEST-PLAN.md)

Please start with the [Specs README](specs/README.md) to understand the architectural decisions and constraints before beginning implementation.

## Loop-Engineering Methodology

This project's specification phase was driven by an iterative loop-engineering methodology, tracking issues, metrics, and tradeoffs across various phases.
*   [Loop Engineering Rules](specs/LOOP-RULES.md)
*   [Phase Task Board](specs/tasks/TASKS.md)
*   [Metrics Log](specs/LOOP-LOG.md)
