import 'package:garage_log/services/server_config.dart';

class ModeloVehiculo {
  int idModelo;
  String marca;
  String modelo;
  int? anio;
  String? combustible;
  String? rutaImagen;

  ModeloVehiculo({
    required this.idModelo,
    required this.marca,
    required this.modelo,
    this.anio,
    this.combustible,
    this.rutaImagen,
  });

  factory ModeloVehiculo.fromJson(Map<String, dynamic> json) =>
      ModeloVehiculo(
        idModelo: json["idModelo"],
        marca: json["marca"] ?? "",
        modelo: json["modelo"] ?? "",
        anio: json["a\u00F1o"],
        combustible: json["combustible"],
        rutaImagen: json["rutaImagen"],
      );

  Map<String, dynamic> toJson() => {
        "idModelo": idModelo,
        "marca": marca,
        "modelo": modelo,
        "a\u00F1o": anio,
        "combustible": combustible,
        "rutaImagen": rutaImagen,
      };

  String get nombreCompleto => "$marca $modelo";

  String get imagenUrl =>
      rutaImagen != null && rutaImagen!.isNotEmpty
          ? "${ServerConfig.baseUrl}/imagenes/$rutaImagen"
          : "";
}
