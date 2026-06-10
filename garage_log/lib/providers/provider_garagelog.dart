import 'package:flutter/material.dart';
import 'package:garage_log/models/models.dart';
import 'package:garage_log/services/services_garagelog.dart';

class ProviderGaragelog with ChangeNotifier {
  List<Vehiculo> _vehiculos = [];

  List<Vehiculo> get vehiculos => _vehiculos;

  Future<List<Vehiculo>> getVehiculos() async {
    _vehiculos = await ServicesGarageLog.instance.getVehiculos();
    return _vehiculos;
  }

  Future<List<Vehiculo>> searchVehiculos(String query) async {
    final results = await ServicesGarageLog.instance.searchVehiculos(query);
    return results;
  }

  Future<List<Mantenimiento>> getMantenimientosByVehiculo(int idVehiculo) async {
    final results =
        await ServicesGarageLog.instance.getMantenimientosByVehiculo(idVehiculo);
    return results;
  }
}
