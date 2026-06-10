import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/models/models.dart';

class DetalleAppbar extends StatelessWidget {
  final int idVehiculo;
  final ModeloVehiculo? modelo;
  final String matricula;

  const DetalleAppbar({
    super.key,
    required this.idVehiculo,
    required this.modelo,
    required this.matricula,
  });

  @override
  Widget build(BuildContext context) {
    final imageUrl = modelo?.imagenUrl ?? "";
    final isDark = Theme.of(context).brightness == Brightness.dark;
    final isLandscape =
        MediaQuery.of(context).orientation == Orientation.landscape;

    return SliverAppBar(
      expandedHeight: isLandscape ? 140 : 260,
      pinned: true,
      backgroundColor: isDark ? AppTheme.darkCard : const Color(0xFF2C2C2C),
      flexibleSpace: FlexibleSpaceBar(
        titlePadding: EdgeInsets.zero,
        title: Container(
          alignment: Alignment.bottomCenter,
          width: double.infinity,
          height: 52,
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                Colors.transparent,
                Colors.black.withValues(alpha: 0.6),
              ],
            ),
          ),
          padding: const EdgeInsets.only(bottom: 12, left: 16, right: 16),
          child: Text(
            modelo?.nombreCompleto ?? matricula,
            style: const TextStyle(
              color: Colors.white,
              fontSize: 19,
              fontWeight: FontWeight.w700,
              letterSpacing: 0.3,
            ),
          ),
        ),
        background: imageUrl.isNotEmpty
            ? Hero(
                tag: 'vehiculo_img_$idVehiculo',
                child: FadeInImage(
                  fit: BoxFit.cover,
                  placeholder:
                      const AssetImage("assets/loading.gif"),
                  image: NetworkImage(imageUrl),
              imageErrorBuilder: (_, __, ___) => _fallback(),
            ),
              )
            : _fallback(),
      ),
    );
  }

  Widget _fallback() {
    return Image.asset(
      "assets/no-image.jpg",
      fit: BoxFit.cover,
    );
  }
}