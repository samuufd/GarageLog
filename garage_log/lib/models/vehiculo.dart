import 'modelo_vehiculo.dart';
import 'mantenimiento.dart';

class Vehiculo {
  int idVehiculo;
  String matricula;
  int? kmIniciales;
  String? fechaCompra;
  String? notas;
  ModeloVehiculo? modeloVehiculo;
  List<Mantenimiento>? mantenimientos;

  Vehiculo({
    required this.idVehiculo,
    required this.matricula,
    this.kmIniciales,
    this.fechaCompra,
    this.notas,
    this.modeloVehiculo,
    this.mantenimientos,
  });

  factory Vehiculo.fromJson(Map<String, dynamic> json) => Vehiculo(
        idVehiculo: json["idVehiculo"],
        matricula: json["matricula"] ?? "",
        kmIniciales: json["kmIniciales"],
        fechaCompra: json["fechaCompra"],
        notas: json["notas"],
        modeloVehiculo: json["modeloVehiculo"] != null
            ? ModeloVehiculo.fromJson(json["modeloVehiculo"])
            : null,
        mantenimientos: json["mantenimientos"] != null
            ? List<Mantenimiento>.from(
                json["mantenimientos"].map((x) => Mantenimiento.fromJson(x)))
            : null,
      );

  Map<String, dynamic> toJson() => {
        "idVehiculo": idVehiculo,
        "matricula": matricula,
        "kmIniciales": kmIniciales,
        "fechaCompra": fechaCompra,
        "notas": notas,
        "modeloVehiculo": modeloVehiculo?.toJson(),
        "mantenimientos": mantenimientos?.map((x) => x.toJson()).toList(),
      };
}
