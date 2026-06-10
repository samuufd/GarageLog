import 'package:flutter/material.dart';
import 'package:garage_log/core/material_garagelog.dart';
import 'package:garage_log/providers/provider_garagelog.dart';
import 'package:garage_log/providers/theme_provider.dart';
import 'package:provider/provider.dart';

class Providers extends StatelessWidget {
  const Providers({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => ProviderGaragelog()),
        ChangeNotifierProvider(create: (_) => ThemeProvider()),
      ],
      child: const MaterialGaragelog(),
    );
  }
}
