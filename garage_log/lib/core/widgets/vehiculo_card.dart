import 'dart:math';
import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/routes/routes_garagelog.dart';

class VehiculoCard extends StatelessWidget {
  final Vehiculo vehiculo;

  const VehiculoCard({super.key, required this.vehiculo});

  @override
  Widget build(BuildContext context) {
    final modelo = vehiculo.modeloVehiculo;
    final imageUrl = modelo?.imagenUrl ?? "";
    final isDark = Theme.of(context).brightness == Brightness.dark;

    final kms = vehiculo.mantenimientos
        ?.map((m) => m.km)
        .whereType<int>()
        .toList();
    final kmActual = kms != null && kms.isNotEmpty
        ? kms.reduce(max)
        : vehiculo.kmIniciales;

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
      child: Material(
        color: isDark ? AppTheme.darkCard : Colors.white,
        borderRadius: BorderRadius.circular(12),
        elevation: 0,
        shadowColor: Colors.transparent,
        child: InkWell(
          borderRadius: BorderRadius.circular(12),
          onTap: () => Navigator.of(context).pushNamed(
            RoutesGaragelog.detalle,
            arguments: vehiculo,
          ),
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(12),
              border: Border.all(
                color: isDark ? AppTheme.darkDivider : AppTheme.divider,
              ),
              boxShadow: isDark ? null : AppTheme.cardShadow,
            ),
            child: Row(
              children: [
                Container(
                  width: 3.5,
                  height: 120,
                  decoration: BoxDecoration(
                    color: AppTheme.accent,
                    borderRadius: const BorderRadius.only(
                      topLeft: Radius.circular(12),
                      bottomLeft: Radius.circular(12),
                    ),
                  ),
                ),
                const SizedBox(width: 12),
                Hero(
                  tag: 'vehiculo_img_${vehiculo.idVehiculo}',
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(10),
                    child: SizedBox(
                      width: 72,
                      height: 72,
                      child: imageUrl.isNotEmpty
                          ? FadeInImage(
                              fit: BoxFit.cover,
                              placeholder: const AssetImage(
                                  "assets/loading.gif"),
                              image: NetworkImage(imageUrl),
                              imageErrorBuilder: (_, __, ___) => Image.asset(
                                  "assets/no-image.jpg",
                                  fit: BoxFit.cover),
                            )
                          : Image.asset("assets/no-image.jpg",
                              fit: BoxFit.cover),
                    ),
                  ),
                ),
                const SizedBox(width: 14),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        modelo?.nombreCompleto ?? "Vehículo",
                        style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              fontWeight: FontWeight.w700,
                            ),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                      const SizedBox(height: 6),
                      Row(
                        children: [
                          Icon(Icons.badge_outlined,
                              size: 14,
                              color: isDark
                                  ? AppTheme.darkTextTertiary
                                  : AppTheme.textTertiary),
                          const SizedBox(width: 5),
                          Text(
                            vehiculo.matricula,
                            style: Theme.of(context)
                                .textTheme
                                .bodySmall
                                ?.copyWith(
                                  color: isDark
                                      ? AppTheme.darkTextSecondary
                                      : AppTheme.textSecondary,
                                  fontWeight: FontWeight.w500,
                                ),
                          ),
                          if (modelo?.anio != null) ...[
                            const SizedBox(width: 10),
                            Icon(Icons.calendar_today_outlined,
                                size: 12,
                                color: isDark
                                    ? AppTheme.darkTextTertiary
                                    : AppTheme.textTertiary),
                            const SizedBox(width: 3),
                            Text(
                              "${modelo!.anio}",
                              style: Theme.of(context)
                                  .textTheme
                                  .bodySmall
                                  ?.copyWith(fontSize: 12),
                            ),
                          ],
                        ],
                      ),
                      if (modelo?.combustible != null) ...[
                        const SizedBox(height: 8),
                        Chip(
                          label: Text(
                            modelo!.combustible!,
                            style: const TextStyle(
                              fontSize: 10,
                              fontWeight: FontWeight.w700,
                              color: AppTheme.accent,
                            ),
                          ),
                          materialTapTargetSize:
                              MaterialTapTargetSize.shrinkWrap,
                          visualDensity: VisualDensity.compact,
                          padding: EdgeInsets.zero,
                          labelPadding:
                              const EdgeInsets.symmetric(horizontal: 6),
                        ),
                      ],
                      if (kmActual != null) ...[
                        const SizedBox(height: 6),
                        Row(
                          children: [
                            Icon(Icons.speed_outlined,
                                size: 13,
                                color: isDark
                                    ? AppTheme.darkTextTertiary
                                    : AppTheme.textTertiary),
                            const SizedBox(width: 4),
                            Text(
                              "$kmActual km",
                              style: Theme.of(context)
                                  .textTheme
                                  .bodySmall
                                  ?.copyWith(
                                    fontWeight: FontWeight.w600,
                                    fontSize: 12,
                                  ),
                            ),
                          ],
                        ),
                      ],
                    ],
                  ),
                ),
                const SizedBox(width: 4),
                Padding(
                  padding: const EdgeInsets.only(right: 12),
                  child: Icon(
                    Icons.chevron_right_rounded,
                    size: 20,
                    color: isDark
                        ? AppTheme.darkTextTertiary
                        : AppTheme.textTertiary,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}