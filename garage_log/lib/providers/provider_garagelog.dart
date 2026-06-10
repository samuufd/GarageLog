import 'package:flutter/material.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/services/services_garagelog.dart';

class ProviderGaragelog with ChangeNotifier {
  List<Vehiculo> _vehiculos = [];
  List<Vehiculo> _searchResults = [];
  List<Mantenimiento> _mantenimientos = [];

  List<Vehiculo> get vehiculos => _vehiculos;
  List<Vehiculo> get searchResults => _searchResults;
  List<Mantenimiento> get mantenimientos => _mantenimientos;

  Future<List<Vehiculo>> getVehiculos() async {
    _vehiculos = await ServicesGarageLog.instance.getVehiculos();
    notifyListeners();
    return _vehiculos;
  }

  Future<List<Vehiculo>> searchVehiculos(String query) async {
    _searchResults = await ServicesGarageLog.instance.searchVehiculos(query);
    notifyListeners();
    return _searchResults;
  }

  Future<List<Mantenimiento>> getMantenimientosByVehiculo(int idVehiculo) async {
    _mantenimientos =
        await ServicesGarageLog.instance.getMantenimientosByVehiculo(idVehiculo);
    notifyListeners();
    return _mantenimientos;
  }
}
