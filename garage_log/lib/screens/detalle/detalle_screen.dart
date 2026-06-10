import 'dart:math';
import 'package:flutter/material.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/screens/detalle/detalle_appbar.dart';
import 'package:garage_log/screens/detalle/detalle_info.dart';
import 'package:garage_log/screens/detalle/detalle_mantenimientos.dart';

class DetalleScreen extends StatelessWidget {
  const DetalleScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final vehiculo = ModalRoute.of(context)!.settings.arguments as Vehiculo;
    final modelo = vehiculo.modeloVehiculo;
    final isDark = Theme.of(context).brightness == Brightness.dark;

    final kms = vehiculo.mantenimientos
        ?.map((m) => m.km)
        .whereType<int>()
        .toList();
    final kmActual = kms != null && kms.isNotEmpty
        ? kms.reduce(max)
        : vehiculo.kmIniciales;

    return Scaffold(
      body: CustomScrollView(
        slivers: [
          DetalleAppbar(
            idVehiculo: vehiculo.idVehiculo,
            modelo: modelo,
            matricula: vehiculo.matricula,
          ),
          SliverToBoxAdapter(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                DetalleSectionTitle(title: "Ficha técnica", isDark: isDark),
                DetalleSpecGrid(
                  vehiculo: vehiculo,
                  modelo: modelo,
                  kmActual: kmActual,
                  isDark: isDark,
                ),
                if (vehiculo.notas != null && vehiculo.notas!.isNotEmpty) ...[
                  const SizedBox(height: 20),
                  DetalleSectionTitle(title: "Notas", isDark: isDark),
                  DetalleNotesCard(notas: vehiculo.notas!, isDark: isDark),
                ],
                const SizedBox(height: 20),
                DetalleSectionTitle(
                  title: "Historial de mantenimiento",
                  isDark: isDark,
                ),
                DetalleMantenimientosList(
                  idVehiculo: vehiculo.idVehiculo,
                  isDark: isDark,
                ),
                const SizedBox(height: 32),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
