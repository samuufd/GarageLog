import 'dart:async';

import 'package:flutter/material.dart';
import 'package:garage_log/providers/providers.dart';
import 'package:garage_log/services/server_config.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await ServerConfig.init();

  FlutterError.onError = (details) {
    if (details.exceptionAsString().contains('Could not decompress')) {
      return;
    }
    FlutterError.presentError(details);
  };
  runZonedGuarded(() => runApp(const Providers()), (error, stack) {
    if (error.toString().contains('Could not decompress')) return;
  });
}
