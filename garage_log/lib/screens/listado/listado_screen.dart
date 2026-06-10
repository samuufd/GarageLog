import 'package:flutter/material.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/core/widgets/widgets.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/providers/provider_garagelog.dart';
import 'package:garage_log/screens/listado/listado_filters.dart';
import 'package:provider/provider.dart';

class ListadoScreen extends StatefulWidget {
  const ListadoScreen({super.key});

  @override
  State<ListadoScreen> createState() => _ListadoScreenState();
}

class _ListadoScreenState extends State<ListadoScreen> {
  String? _filterCombustible;
  String? _filterMarca;
  String? _filterAnio;

  bool get _hasActiveFilters =>
      _filterCombustible != null ||
      _filterMarca != null ||
      _filterAnio != null;

  List<Vehiculo> _applyFilters(List<Vehiculo> vehiculos) {
    return vehiculos.where((v) {
      final m = v.modeloVehiculo;
      if (_filterCombustible != null && m?.combustible != _filterCombustible) return false; 
      if (_filterMarca != null && m?.marca != _filterMarca) return false;
      if (_filterAnio != null && m?.anio.toString() != _filterAnio) return false;
      return true;
    }).toList();
  }

  void _showFilterSheet(List<Vehiculo> vehiculos) {
    final combustibles = <String>{};
    final marcas = <String>{};
    final anios = <String>{};
    for (final v in vehiculos) {
      final m = v.modeloVehiculo;
      if (m != null) {
        if (m.combustible != null) combustibles.add(m.combustible!);
        marcas.add(m.marca);
        if (m.anio != null) anios.add(m.anio.toString());
      }
    }

    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(16)),
      ),
      builder: (_) => StatefulBuilder(
        builder: (ctx, setSheetState) => FilterSheet(
          combustible: _filterCombustible,
          marca: _filterMarca,
          anio: _filterAnio,
          combustibles: combustibles.toList()..sort(),
          marcas: marcas.toList()..sort(),
          anios: anios.toList()..sort(),
          hasActiveFilters: _hasActiveFilters,
          onCombustibleChanged: (v) {
            setSheetState(() => _filterCombustible = v);
            setState(() {});
          },
          onMarcaChanged: (v) {
            setSheetState(() => _filterMarca = v);
            setState(() {});
          },
          onAnioChanged: (v) {
            setSheetState(() => _filterAnio = v);
            setState(() {});
          },
          onClear: () {
            setSheetState(() {
              _filterCombustible = null;
              _filterMarca = null;
              _filterAnio = null;
            });
            setState(() {});
          },
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<ProviderGaragelog>(context, listen: false);
    final isDark = Theme.of(context).brightness == Brightness.dark;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Listado completo'),
        centerTitle: true,
        actions: [
          Stack(
            clipBehavior: Clip.none,
            children: [
              IconButton(
                icon: const Icon(Icons.filter_list_rounded),
                onPressed: () => _showFilterSheet(provider.vehiculos),
              ),
              if (_hasActiveFilters)
                Positioned(
                  right: 4,
                  top: 4,
                  child: Container(
                    width: 10,
                    height: 10,
                    decoration: const BoxDecoration(
                      color: AppTheme.accent,
                      shape: BoxShape.circle,
                    ),
                  ),
                ),
            ],
          ),
        ],
        bottom: PreferredSize(
          preferredSize: Size.fromHeight(_hasActiveFilters ? 44 : 2),
          child: _hasActiveFilters
              ? Container(
                  padding: const EdgeInsets.only(left: 16, right: 16, bottom: 8),
                  child: Row(
                    children: [
                      if (_filterCombustible != null)
                        ActiveFilterChip(
                          label: _filterCombustible!,
                          onRemove: () => setState(() => _filterCombustible = null),
                        ),
                      if (_filterMarca != null)
                        ActiveFilterChip(
                          label: _filterMarca!,
                          onRemove: () => setState(() => _filterMarca = null),
                        ),
                      if (_filterAnio != null)
                        ActiveFilterChip(
                          label: _filterAnio!,
                          onRemove: () => setState(() => _filterAnio = null),
                        ),
                    ],
                  ),
                )
              : Container(
                  color: AppTheme.accent.withValues(alpha: 0.4),
                  height: 2,
                ),
        ),
      ),
      body: FutureBuilder<List<Vehiculo>>(
        future: provider.getVehiculos(),
        builder: (_, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return const ErrorState(
              message: 'Error de conexión',
              subtitle: 'No se pudieron cargar los vehículos.\nVerifica la conexión con el servidor.',
            );
          }
          final vehiculos = snapshot.data ?? [];
          if (vehiculos.isEmpty) {
            return const EmptyState(
              icon: Icons.directions_car_rounded,
              message: 'No hay vehículos registrados',
            );
          }
          final filtered = _applyFilters(vehiculos);
          if (filtered.isEmpty) {
            return Center(
              child: Padding(
                padding: const EdgeInsets.all(32),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Container(
                      width: 48,
                      height: 48,
                      decoration: BoxDecoration(
                        color: isDark
                            ? AppTheme.darkDivider
                            : AppTheme.divider.withValues(alpha: 0.4),
                        shape: BoxShape.circle,
                      ),
                      child: const Icon(
                        Icons.search_off_rounded,
                        size: 22,
                        color: AppTheme.textTertiary,
                      ),
                    ),
                    const SizedBox(height: 12),
                    Text(
                      'Sin resultados',
                      style: TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.w500,
                        color: isDark
                            ? AppTheme.darkTextSecondary
                            : AppTheme.textSecondary,
                      ),
                    ),
                    const SizedBox(height: 4),
                    TextButton(
                      onPressed: () => setState(() {
                        _filterCombustible = null;
                        _filterMarca = null;
                        _filterAnio = null;
                      }),
                      child: const Text('Limpiar filtros'),
                    ),
                  ],
                ),
              ),
            );
          }
          return ListView.builder(
            padding: const EdgeInsets.symmetric(vertical: 8),
            itemCount: filtered.length,
            itemBuilder: (_, index) =>
                VehiculoCard(vehiculo: filtered[index]),
          );
        },
      ),
    );
  }
}
