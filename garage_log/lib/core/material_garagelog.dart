import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/providers/theme_provider.dart';
import 'package:garage_log/routes/routes_garagelog.dart';
import 'package:provider/provider.dart';

class MaterialGaragelog extends StatelessWidget {
  const MaterialGaragelog({super.key});

  @override
  Widget build(BuildContext context) {
    final themeProvider = Provider.of<ThemeProvider>(context);

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      routes: RoutesGaragelog.routes,
      initialRoute: RoutesGaragelog.home,
      title: 'GarageLog',
      themeMode: themeProvider.themeMode,
      theme: AppTheme.themeData(false),
      darkTheme: AppTheme.themeData(true),
    );
  }
}
