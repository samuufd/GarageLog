import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';

class AppDecorations {
  static BoxDecoration containerCard(bool isDark) => BoxDecoration(
        color: isDark ? AppTheme.darkCard : Colors.white,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(
            color: isDark ? AppTheme.darkDivider : AppTheme.divider),
        boxShadow: isDark ? null : AppTheme.cardShadow,
      );

  static BoxDecoration containerCardRounded(bool isDark, double radius) =>
      BoxDecoration(
        color: isDark ? AppTheme.darkCard : Colors.white,
        borderRadius: BorderRadius.circular(radius),
        border: Border.all(
            color: isDark ? AppTheme.darkDivider : AppTheme.divider),
      );

  static BoxDecoration iconCircle(Color color) => BoxDecoration(
        color: color,
        shape: BoxShape.circle,
      );

  static BoxDecoration accentBadge({double radius = 6}) => BoxDecoration(
        color: AppTheme.accent.withValues(alpha: 0.15),
        borderRadius: BorderRadius.circular(radius),
      );

  static BoxDecoration surfaceBox(bool isDark, {double radius = 8}) =>
      BoxDecoration(
        color: isDark ? AppTheme.darkSurface : AppTheme.surface,
        borderRadius: BorderRadius.circular(radius),
      );
}
