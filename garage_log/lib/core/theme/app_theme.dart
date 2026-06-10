import 'package:flutter/material.dart';

class AppTheme {
  AppTheme._();

  static const Color primary = Color(0xFF1E293B);
  static const Color accent = Color(0xFFF59E0B);
  static const Color surface = Color(0xFFF8F6F3);
  static const Color card = Color(0xFFFFFDFA);
  static const Color textPrimary = Color(0xFF0F172A);
  static const Color textSecondary = Color(0xFF64748B);
  static const Color textTertiary = Color(0xFF94A3B8);
  static const Color divider = Color(0xFFE8E4DF);
  static const Color error = Color(0xFFEF4444);

  static const Color darkSurface = Color(0xFF1A1A1A);
  static const Color darkCard = Color(0xFF252525);
  static const Color darkDivider = Color(0xFF333333);
  static const Color darkTextPrimary = Color(0xFFF5F5F5);
  static const Color darkTextSecondary = Color(0xFFB0B0B0);
  static const Color darkTextTertiary = Color(0xFF808080);

  static List<BoxShadow> get cardShadow => [
        BoxShadow(
          color: Colors.black.withValues(alpha: 0.05),
          blurRadius: 8,
          offset: const Offset(0, 3),
        ),
        BoxShadow(
          color: Colors.black.withValues(alpha: 0.02),
          blurRadius: 2,
          offset: const Offset(0, 1),
        ),
      ];

  static BoxDecoration cardDecoration(bool isDark) => BoxDecoration(
        color: isDark ? AppTheme.darkCard : AppTheme.card,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(
            color: isDark ? AppTheme.darkDivider : AppTheme.divider),
        boxShadow: isDark ? null : AppTheme.cardShadow,
      );

  static ThemeData themeData(bool isDark) {
    return ThemeData(
      useMaterial3: false,
      scaffoldBackgroundColor: isDark ? AppTheme.darkSurface : AppTheme.surface,
      colorScheme: ColorScheme(
        brightness: isDark ? Brightness.dark : Brightness.light,
        primary: AppTheme.primary,
        secondary: AppTheme.accent,
        surface: isDark ? AppTheme.darkSurface : AppTheme.surface,
        onPrimary: Colors.white,
        onSecondary: Colors.white,
        onSurface: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
        error: AppTheme.error,
        onError: Colors.white,
      ),
      appBarTheme: AppBarTheme(
        backgroundColor:
            isDark ? AppTheme.darkCard : const Color(0xFF2C2C2C),
        foregroundColor:
            isDark ? AppTheme.darkTextPrimary : Colors.white,
        elevation: 0,
        centerTitle: true,
        titleTextStyle: TextStyle(
          fontSize: 17,
          fontWeight: FontWeight.w700,
          letterSpacing: 0.5,
          color: isDark ? AppTheme.darkTextPrimary : Colors.white,
        ),
        iconTheme: const IconThemeData(color: AppTheme.accent),
      ),
      cardTheme: CardThemeData(
        color: isDark ? AppTheme.darkCard : AppTheme.card,
        elevation: 0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(12),
          side: BorderSide(
              color: isDark ? AppTheme.darkDivider : AppTheme.divider,
              width: 1),
        ),
        margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
      ),
      textTheme: TextTheme(
        headlineLarge: TextStyle(
          fontSize: 30,
          fontWeight: FontWeight.w800,
          letterSpacing: -0.8,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.2,
        ),
        headlineMedium: TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.w700,
          letterSpacing: -0.4,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.3,
        ),
        titleLarge: TextStyle(
          fontSize: 18,
          fontWeight: FontWeight.w700,
          letterSpacing: -0.2,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.3,
        ),
        titleMedium: TextStyle(
          fontSize: 15,
          fontWeight: FontWeight.w600,
          letterSpacing: 0,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.3,
        ),
        titleSmall: TextStyle(
          fontSize: 13,
          fontWeight: FontWeight.w600,
          letterSpacing: 0.2,
          color:
              isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary,
          height: 1.3,
        ),
        bodyLarge: TextStyle(
          fontSize: 15,
          letterSpacing: 0.15,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.5,
        ),
        bodyMedium: TextStyle(
          fontSize: 14,
          letterSpacing: 0.1,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          height: 1.5,
        ),
        bodySmall: TextStyle(
          fontSize: 13,
          letterSpacing: 0.05,
          color:
              isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary,
          height: 1.4,
        ),
        labelLarge: TextStyle(
          fontSize: 13,
          fontWeight: FontWeight.w600,
          letterSpacing: 0.3,
          color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
        ),
        labelSmall: TextStyle(
          fontSize: 11,
          fontWeight: FontWeight.w500,
          letterSpacing: 0.3,
          color:
              isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary,
        ),
      ),
      dividerTheme: DividerThemeData(
        color: isDark ? AppTheme.darkDivider : AppTheme.divider,
        thickness: 1,
        space: 1,
      ),
      inputDecorationTheme: InputDecorationTheme(
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: BorderSide(
              color: isDark ? AppTheme.darkDivider : AppTheme.divider),
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: BorderSide(
              color: isDark ? AppTheme.darkDivider : AppTheme.divider),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
          borderSide: const BorderSide(color: AppTheme.accent, width: 2),
        ),
        filled: true,
        fillColor: isDark ? AppTheme.darkCard : Colors.white,
        contentPadding:
            const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        hintStyle: TextStyle(
          color:
              isDark ? AppTheme.darkTextTertiary : AppTheme.textTertiary,
          fontSize: 14,
        ),
      ),
      drawerTheme: DrawerThemeData(
        backgroundColor: isDark ? AppTheme.darkSurface : Colors.white,
      ),
      progressIndicatorTheme: ProgressIndicatorThemeData(
        color: AppTheme.accent,
        linearTrackColor:
            isDark ? AppTheme.darkDivider : AppTheme.divider,
      ),
      iconTheme: IconThemeData(
        color: isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary,
        size: 24,
      ),
      chipTheme: ChipThemeData(
        backgroundColor:
            AppTheme.accent.withValues(alpha: isDark ? 0.15 : 0.1),
        labelStyle: const TextStyle(
          fontSize: 12,
          fontWeight: FontWeight.w600,
          color: AppTheme.accent,
        ),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8),
        ),
        side: BorderSide.none,
        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 0),
      ),
    );
  }
}
