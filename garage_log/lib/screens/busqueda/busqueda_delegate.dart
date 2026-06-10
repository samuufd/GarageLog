import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/core/widgets/widgets.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/providers/provider_garagelog.dart';
import 'package:provider/provider.dart';

class BusquedaDelegate extends SearchDelegate {
  @override
  String? get searchFieldLabel => "Buscar vehículo";

  @override
  ThemeData appBarTheme(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;
    return Theme.of(context).copyWith(
      appBarTheme: AppBarTheme(
        backgroundColor: isDark ? AppTheme.darkCard : const Color(0xFF2C2C2C),
        foregroundColor: isDark ? AppTheme.darkTextPrimary : Colors.white,
        elevation: 0,
        iconTheme: const IconThemeData(color: AppTheme.accent),
      ),
      inputDecorationTheme: InputDecorationTheme(
        hintStyle: TextStyle(
          color: isDark ? Colors.white60 : AppTheme.textTertiary,
        ),
        border: InputBorder.none,
        enabledBorder: InputBorder.none,
        focusedBorder: InputBorder.none,
        fillColor: Colors.transparent,
        filled: false,
      ),
    );
  }

  @override
  List<Widget>? buildActions(BuildContext context) {
    return [
      if (query.isNotEmpty)
        IconButton(
          onPressed: () => query = "",
          icon: const Icon(Icons.highlight_remove),
        ),
    ];
  }

  @override
  Widget? buildLeading(BuildContext context) {
    return IconButton(
      onPressed: () => close(context, null),
      icon: const Icon(Icons.arrow_back),
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return _buildSearchList(context);
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return _buildSearchList(context);
  }

  Widget _buildSearchList(BuildContext context) {
    if (query.isEmpty) {
      return const EmptyState(
        icon: Icons.search_rounded,
        message: "Escribe para buscar vehículos",
        subtitle: "Busca por matrícula, marca o modelo",
      );
    }
    final provider = Provider.of<ProviderGaragelog>(context, listen: false);
    return FutureBuilder<List<Vehiculo>>(
      future: provider.searchVehiculos(query),
      builder: (_, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        }
        if (snapshot.hasError) {
          return const ErrorState(
            message: "Error de conexión",
            subtitle: "No se pudieron cargar los resultados",
          );
        }
        final datos = snapshot.data ?? [];
        if (datos.isEmpty) {
          return const EmptyState(
            icon: Icons.search_off_rounded,
            message: "No se encontraron vehículos",
            subtitle: "Prueba con otro término de búsqueda",
          );
        }
        return ListView.builder(
          padding: const EdgeInsets.symmetric(vertical: 8),
          itemCount: datos.length,
          itemBuilder: (_, index) => VehiculoCard(vehiculo: datos[index]),
        );
      },
    );
  }
}