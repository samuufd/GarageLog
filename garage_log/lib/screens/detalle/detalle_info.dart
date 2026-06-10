import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/models/models.dart';

class DetalleSectionTitle extends StatelessWidget {
  final String title;
  final bool isDark;
  const DetalleSectionTitle({
    super.key,
    required this.title,
    required this.isDark,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 16, right: 16, bottom: 10, top: 4),
      child: Row(
        children: [
          Container(
            width: 3.5,
            height: 18,
            decoration: BoxDecoration(
              color: AppTheme.accent,
              borderRadius: BorderRadius.circular(2),
            ),
          ),
          const SizedBox(width: 12),
          Text(
            title,
            style: TextStyle(
              fontSize: 17,
              fontWeight: FontWeight.w700,
              color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
            ),
          ),
        ],
      ),
    );
  }
}

class DetalleSpecGrid extends StatelessWidget {
  final Vehiculo vehiculo;
  final ModeloVehiculo? modelo;
  final int? kmActual;
  final bool isDark;

  const DetalleSpecGrid({
    super.key,
    required this.vehiculo,
    required this.modelo,
    required this.kmActual,
    required this.isDark,
  });

  @override
  Widget build(BuildContext context) {
    final specs = <MapEntry<String, String>>[];
    if (modelo != null) {
      specs.add(MapEntry("Marca", modelo!.marca));
      specs.add(MapEntry("Modelo", modelo!.modelo));
      if (modelo!.anio != null) {
        specs.add(MapEntry("Año", modelo!.anio.toString()));
      }
      if (modelo!.combustible != null) {
        specs.add(MapEntry("Combustible", modelo!.combustible!));
      }
    }
    specs.add(MapEntry("Matrícula", vehiculo.matricula));
    if (vehiculo.kmIniciales != null) {
      specs.add(MapEntry("KM iniciales", "${vehiculo.kmIniciales} km"));
    }
    if (kmActual != null) {
      specs.add(MapEntry("KM actuales", "$kmActual km"));
    }
    if (vehiculo.fechaCompra != null) {
      specs.add(MapEntry("Fecha compra", vehiculo.fechaCompra!));
    }

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Container(
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: isDark ? AppTheme.darkCard : Colors.white,
          borderRadius: BorderRadius.circular(12),
          border: Border.all(
            color: isDark ? AppTheme.darkDivider : AppTheme.divider,
          ),
          boxShadow: isDark ? null : AppTheme.cardShadow,
        ),
        child: Column(
          children: [
            for (int i = 0; i < specs.length; i += 2)
              Column(
                children: [
                  if (i > 0)
                    Divider(
                      height: 1,
                      color: isDark ? AppTheme.darkDivider : AppTheme.divider,
                    ),
                  Row(
                    children: [
                      Expanded(
                        child: Padding(
                          padding: EdgeInsets.only(
                            top: i > 0 ? 14 : 0,
                            bottom: i + 1 < specs.length ? 14 : 0,
                            right: 8,
                          ),
                          child: DetalleSpecItem(
                            label: specs[i].key,
                            value: specs[i].value,
                            isDark: isDark,
                          ),
                        ),
                      ),
                      Container(
                        width: 1,
                        height: 36,
                        color: isDark ? AppTheme.darkDivider : AppTheme.divider,
                      ),
                      if (i + 1 < specs.length)
                        Expanded(
                          child: Padding(
                            padding: const EdgeInsets.only(left: 8),
                            child: DetalleSpecItem(
                              label: specs[i + 1].key,
                              value: specs[i + 1].value,
                              isDark: isDark,
                            ),
                          ),
                        )
                      else
                        const Expanded(child: SizedBox()),
                    ],
                  ),
                ],
              ),
          ],
        ),
      ),
    );
  }
}

class DetalleSpecItem extends StatelessWidget {
  final String label;
  final String value;
  final bool isDark;

  const DetalleSpecItem({
    super.key,
    required this.label,
    required this.value,
    required this.isDark,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: TextStyle(
            fontSize: 11,
            fontWeight: FontWeight.w500,
            color: isDark ? AppTheme.darkTextTertiary : AppTheme.textTertiary,
          ),
        ),
        const SizedBox(height: 4),
        Text(
          value,
          style: TextStyle(
            fontSize: 15,
            fontWeight: FontWeight.w700,
            color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
          ),
        ),
      ],
    );
  }
}

class DetalleNotesCard extends StatelessWidget {
  final String notas;
  final bool isDark;

  const DetalleNotesCard({
    super.key,
    required this.notas,
    required this.isDark,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Container(
        width: double.infinity,
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: isDark ? AppTheme.darkCard : Colors.white,
          borderRadius: BorderRadius.circular(12),
          border: Border.all(
            color: isDark ? AppTheme.darkDivider : AppTheme.divider,
          ),
          boxShadow: isDark ? null : AppTheme.cardShadow,
        ),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Icon(
              Icons.notes_rounded,
              size: 18,
              color: isDark ? AppTheme.accent : AppTheme.primary,
            ),
            const SizedBox(width: 10),
            Expanded(
              child: Text(
                notas,
                style: TextStyle(
                  fontSize: 14,
                  height: 1.6,
                  color: isDark
                      ? AppTheme.darkTextPrimary
                      : AppTheme.textPrimary,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
