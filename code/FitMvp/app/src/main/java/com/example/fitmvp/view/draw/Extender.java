//package com.example.fitmvp.view.draw;
//
//import androidx.lifecycle.LifecycleOwner;
//import androidx.camera.extensions.BokehExtender;
//public class Extender {
//
//
//    void onCreate() {
//        // Create a Builder same as in normal workflow.
//        ImageCaptureConfig.Builder builder = new ImageCaptureConfig.Builder();
//
//        // Create a Extender object which can be used to apply extension
//        // configurations.
//        BokehImageCaptureExtender bokehImageCapture = new
//                BokehImageCaptureExtender(builder);
//
//        // Query if extension is available (optional).
//        if (bokehImageCapture.isExtensionAvailable()) {
//            // Enable the extension if available.
//            bokehImageCapture.enableExtension();
//        }
//
//        // Finish constructing configuration with the same flow as when not using
//        // extensions.
//        ImageCaptureConfig config = builder.build();
//        ImageCapture useCase = new ImageCapture(config);
//        CameraX.bindToLifecycle((LifecycleOwner)this, useCase);
//    }
//
//
//}
