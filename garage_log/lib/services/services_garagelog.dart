import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:garage_log/models/models.dart';
import 'package:garage_log/services/server_config.dart';

class ServicesGarageLog {
  ServicesGarageLog._();

  static final ServicesGarageLog instance = ServicesGarageLog._();

  static const Duration _timeout = Duration(seconds: 15);

  String get _baseUrl => ServerConfig.baseUrl;

  Future<List<Vehiculo>> getVehiculos() async {
    final uri = Uri.parse("$_baseUrl/vehiculos");
    final response = await http.get(uri, headers: _headers()).timeout(_timeout);
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((x) => Vehiculo.fromJson(x)).toList();
    }
    throw Exception("Error HTTP ${response.statusCode}");
  }

  Future<List<Vehiculo>> searchVehiculos(String query) async {
    final uri = Uri.parse("$_baseUrl/vehiculos/search").replace(queryParameters: {"texto": query});
    final response = await http.get(uri, headers: _headers()).timeout(_timeout);
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((x) => Vehiculo.fromJson(x)).toList();
    }
    throw Exception("Error HTTP ${response.statusCode}");
  }

  Future<List<Mantenimiento>> getMantenimientosByVehiculo(int idVehiculo) async {
    final uri =
        Uri.parse("$_baseUrl/mantenimientos/vehiculo/$idVehiculo");
    final response = await http.get(uri, headers: _headers()).timeout(_timeout);
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((x) => Mantenimiento.fromJson(x)).toList();
    }
    throw Exception("Error HTTP ${response.statusCode}");
  }

  Map<String, String> _headers() => {
        "accept": "application/json",
        "Content-Type": "application/json",
      };
}
