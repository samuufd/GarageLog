import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/core/widgets/widgets.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/providers/provider_garagelog.dart';
import 'package:provider/provider.dart';

class DetalleMantenimientosList extends StatelessWidget {
  final int idVehiculo;
  final bool isDark;

  const DetalleMantenimientosList({
    super.key,
    required this.idVehiculo,
    required this.isDark,
  });

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<ProviderGaragelog>(context, listen: false);

    return FutureBuilder<List<Mantenimiento>>(
      future: provider.getMantenimientosByVehiculo(idVehiculo),
      builder: (_, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Padding(
            padding: EdgeInsets.all(32),
            child: Center(child: CircularProgressIndicator()),
          );
        }
        if (snapshot.hasError) {
          return const Padding(
            padding: EdgeInsets.all(32),
            child: ErrorState(message: "Error al cargar mantenimientos"),
          );
        }
        final mantenimientos = snapshot.data ?? [];
        if (mantenimientos.isEmpty) {
          return const Padding(
            padding: EdgeInsets.all(32),
            child: EmptyState(
              icon: Icons.build_outlined,
              message: "Sin mantenimientos registrados",
            ),
          );
        }
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Column(
            children: [
              _Summary(
                count: mantenimientos.length,
                totalCost: mantenimientos.fold<double>(
                  0, (sum, m) => sum + (m.costeTotal ?? 0),
                ),
                isDark: isDark,
              ),
              const SizedBox(height: 12),
              for (final m in mantenimientos) _Entry(mantenimiento: m, isDark: isDark),
            ],
          ),
        );
      },
    );
  }
}

class _Summary extends StatelessWidget {
  final int count;
  final double totalCost;
  final bool isDark;

  const _Summary({required this.count, required this.totalCost, required this.isDark});

  @override
  Widget build(BuildContext context) {
    final c2 = isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary;
    final c3 = isDark ? AppTheme.darkTextTertiary : AppTheme.textTertiary;

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 6),
      child: Row(children: [
        Icon(Icons.build_outlined, size: 16, color: c3),
        const SizedBox(width: 8),
        Text("$count mantenimiento${count != 1 ? "s" : ""}",
          style: TextStyle(fontSize: 13, fontWeight: FontWeight.w500, color: c2)),
        const Spacer(),
        if (totalCost > 0)
          Text("${totalCost.toStringAsFixed(2)} € total",
            style: TextStyle(fontSize: 13, fontWeight: FontWeight.w600, color: c2)),
      ]),
    );
  }
}

class _Entry extends StatefulWidget {
  final Mantenimiento mantenimiento;
  final bool isDark;

  const _Entry({required this.mantenimiento, required this.isDark});
  @override State<_Entry> createState() => _EntryState();
}

class _EntryState extends State<_Entry> {
  bool _expanded = false;

  @override
  Widget build(BuildContext context) {
    final m = widget.mantenimiento;
    final isDark = widget.isDark;
    final textPrimary = isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary;
    final textSecondary = isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary;
    final textTertiary = isDark ? AppTheme.darkTextTertiary : AppTheme.textTertiary;
    final hasPiezas = m.piezas != null && m.piezas!.isNotEmpty;

    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: _Card(isDark: isDark, child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _AccentBar(height: hasPiezas && _expanded ? 80 : 50),
          const SizedBox(width: 12),
          Expanded(
            child: GestureDetector(
              onTap: hasPiezas ? () => setState(() => _expanded = !_expanded) : null,
              behavior: HitTestBehavior.opaque,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(crossAxisAlignment: CrossAxisAlignment.start, children: [
                    Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                      Text(m.fecha, style: TextStyle(fontSize: 12, fontWeight: FontWeight.w500, color: textTertiary)),
                      const SizedBox(height: 2),
                      Text(m.observaciones?.isNotEmpty == true ? m.observaciones! : "Mantenimiento",
                        style: TextStyle(fontSize: 14, fontWeight: FontWeight.w600, color: textPrimary)),
                    ])),
                    if (m.costeTotal != null)
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                        decoration: BoxDecoration(
                          color: AppTheme.accent.withValues(alpha: 0.15),
                          borderRadius: BorderRadius.circular(6),
                        ),
                        child: Text("${m.costeTotal!.toStringAsFixed(2)} €",
                          style: const TextStyle(fontSize: 12, fontWeight: FontWeight.w700, color: AppTheme.accent)),
                      ),
                  ]),
                  if (m.km != null) ...[
                    const SizedBox(height: 6),
                    Row(children: [
                      Icon(Icons.speed_outlined, size: 13, color: textTertiary),
                      const SizedBox(width: 4),
                      Text("${m.km} km", style: TextStyle(fontSize: 12, color: textSecondary)),
                    ]),
                  ],
                  if (hasPiezas) ...[
                    const SizedBox(height: 8),
                    Row(children: [
                      Icon(Icons.build_outlined, size: 14, color: AppTheme.accent),
                      const SizedBox(width: 4),
                      Text("${m.piezas!.length} pieza${m.piezas!.length != 1 ? "s" : ""}",
                        style: TextStyle(fontSize: 12, fontWeight: FontWeight.w600, color: AppTheme.accent)),
                      const Spacer(),
                      Icon(_expanded ? Icons.expand_less_rounded : Icons.chevron_right_rounded,
                        size: 18, color: textSecondary),
                    ]),
                    if (_expanded) ...[
                      const SizedBox(height: 8),
                      Divider(height: 1, color: isDark ? AppTheme.darkDivider : AppTheme.divider),
                      const SizedBox(height: 10),
                      ...m.piezas!.map((mp) => _PiezaRow(pieza: mp, isDark: isDark)),
                    ],
                  ],
                ],
              ),
            ),
          ),
        ],
      )),
    );
  }
}

class _PiezaRow extends StatelessWidget {
  final MantenimientoPieza pieza;
  final bool isDark;

  const _PiezaRow({required this.pieza, required this.isDark});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 4),
      padding: const EdgeInsets.all(8),
      decoration: BoxDecoration(
        color: isDark ? AppTheme.darkSurface : AppTheme.surface,
        borderRadius: BorderRadius.circular(6),
      ),
      child: Row(children: [
        Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(pieza.pieza?.nombre ?? "Pieza #${pieza.idMantenimientoPieza}",
            style: TextStyle(fontSize: 13, fontWeight: FontWeight.w600,
              color: isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary)),
          const SizedBox(height: 2),
          Text("${pieza.cantidad}x  ${pieza.precioUnitario?.toStringAsFixed(2) ?? "?"} €/ud",
            style: TextStyle(fontSize: 12,
              color: isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary)),
        ])),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
          decoration: BoxDecoration(
            color: (isDark ? AppTheme.accent : AppTheme.primary).withValues(alpha: 0.06),
            borderRadius: BorderRadius.circular(6),
          ),
          child: Text("${pieza.subtotal.toStringAsFixed(2)} €",
            style: TextStyle(fontWeight: FontWeight.w700, fontSize: 12,
              color: isDark ? AppTheme.accent : AppTheme.primary)),
        ),
      ]),
    );
  }
}

class _Card extends StatelessWidget {
  final bool isDark;
  final Widget child;

  const _Card({required this.isDark, required this.child});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: isDark ? AppTheme.darkCard : Colors.white,
        borderRadius: BorderRadius.circular(10),
        border: Border.all(color: isDark ? AppTheme.darkDivider : AppTheme.divider),
      ),
      child: child,
    );
  }
}

class _AccentBar extends StatelessWidget {
  final double height;

  const _AccentBar({required this.height});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 3.5, height: height,
      decoration: BoxDecoration(
        color: AppTheme.accent,
        borderRadius: BorderRadius.circular(2),
      ),
    );
  }
}
