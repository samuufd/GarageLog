import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';

class FilterSection extends StatelessWidget {
  final String label;
  final List<String> values;
  final String? selected;
  final ValueChanged<String?> onSelected;

  const FilterSection({
    super.key,
    required this.label,
    required this.values,
    required this.selected,
    required this.onSelected,
  });

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: TextStyle(
            fontSize: 13,
            fontWeight: FontWeight.w600,
            color:
                isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary,
          ),
        ),
        const SizedBox(height: 8),
        Wrap(
          spacing: 8,
          runSpacing: 6,
          children: [
            FilterChip(
              label: Text(
                'Todos',
                style: TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.w500,
                  color: selected == null
                      ? Colors.white
                      : isDark
                          ? AppTheme.darkTextSecondary
                          : AppTheme.textSecondary,
                ),
              ),
              selected: selected == null,
              onSelected: (_) => onSelected(null),
              selectedColor: AppTheme.accent,
              checkmarkColor: Colors.white,
              backgroundColor: isDark ? AppTheme.darkCard : Colors.white,
              side: BorderSide(
                color: selected == null
                    ? AppTheme.accent
                    : isDark
                        ? AppTheme.darkDivider
                        : AppTheme.divider,
              ),
              materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
              visualDensity: VisualDensity.compact,
            ),
            for (final v in values)
              FilterChip(
                label: Text(
                  v,
                  style: TextStyle(
                    fontSize: 12,
                    fontWeight: FontWeight.w500,
                    color: selected == v
                        ? Colors.white
                        : isDark
                            ? AppTheme.darkTextPrimary
                            : AppTheme.textPrimary,
                  ),
                ),
                selected: selected == v,
                onSelected: (_) => onSelected(v),
                selectedColor: AppTheme.accent,
                checkmarkColor: Colors.white,
                backgroundColor: isDark ? AppTheme.darkCard : Colors.white,
                side: BorderSide(
                  color: selected == v
                      ? AppTheme.accent
                      : isDark
                          ? AppTheme.darkDivider
                          : AppTheme.divider,
                ),
                materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                visualDensity: VisualDensity.compact,
              ),
          ],
        ),
      ],
    );
  }
}

class ActiveFilterChip extends StatelessWidget {
  final String label;
  final VoidCallback onRemove;

  const ActiveFilterChip({super.key, required this.label, required this.onRemove});

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;
    return Padding(
      padding: const EdgeInsets.only(right: 8),
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
        decoration: BoxDecoration(
          color: AppTheme.accent.withValues(alpha: 0.12),
          borderRadius: BorderRadius.circular(6),
          border: Border.all(
            color: AppTheme.accent.withValues(alpha: 0.25),
          ),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              label,
              style: TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w600,
                color:
                    isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
              ),
            ),
            const SizedBox(width: 4),
            GestureDetector(
              onTap: onRemove,
              child: Icon(
                Icons.close_rounded,
                size: 14,
                color: isDark
                    ? AppTheme.darkTextSecondary
                    : AppTheme.textTertiary,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class FilterSheet extends StatelessWidget {
  final String? combustible;
  final String? marca;
  final String? anio;
  final List<String> combustibles;
  final List<String> marcas;
  final List<String> anios;
  final bool hasActiveFilters;
  final ValueChanged<String?> onCombustibleChanged;
  final ValueChanged<String?> onMarcaChanged;
  final ValueChanged<String?> onAnioChanged;
  final VoidCallback onClear;

  const FilterSheet({
    super.key,
    required this.combustible,
    required this.marca,
    required this.anio,
    required this.combustibles,
    required this.marcas,
    required this.anios,
    required this.hasActiveFilters,
    required this.onCombustibleChanged,
    required this.onMarcaChanged,
    required this.onAnioChanged,
    required this.onClear,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(20, 12, 20, 24),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Center(
            child: Container(
              width: 36,
              height: 4,
              decoration: BoxDecoration(
                color: Colors.grey.shade300,
                borderRadius: BorderRadius.circular(2),
              ),
            ),
          ),
          const SizedBox(height: 16),
          Text(
            'Filtrar por...',
            style: Theme.of(context).textTheme.titleLarge,
          ),
          const SizedBox(height: 16),
          FilterSection(
            label: 'Combustible',
            values: combustibles,
            selected: combustible,
            onSelected: onCombustibleChanged,
          ),
          const SizedBox(height: 14),
          FilterSection(
            label: 'Marca',
            values: marcas,
            selected: marca,
            onSelected: onMarcaChanged,
          ),
          const SizedBox(height: 14),
          FilterSection(
            label: 'A\u00F1o',
            values: anios,
            selected: anio,
            onSelected: onAnioChanged,
          ),
          const SizedBox(height: 20),
          SizedBox(
            width: double.infinity,
            child: OutlinedButton.icon(
              onPressed:
                  hasActiveFilters ? onClear : null,
              icon: const Icon(Icons.clear_all_rounded, size: 18),
              label: const Text('Limpiar filtros'),
            ),
          ),
        ],
      ),
    );
  }
}
