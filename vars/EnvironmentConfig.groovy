#!/usr/bin/env groovy

/**
 * Shared Library: Environment Configuration
 * This script defines the required environment variables for ZAPScanner.
 */

def configure() {
    echo "Configuring environment variables for ZAP Scanner..."

    // Set the environment variable dynamically
    if (!env.TARGET_URL) {
        env.TARGET_URL = 'https://badgeting-web-app.vercel.app/' // Default target URL
        echo "TARGET_URL is not set. Using default: ${env.TARGET_URL}"
    } else {
        echo "TARGET_URL is already set: ${env.TARGET_URL}"
    }
}
