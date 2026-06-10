import 'mantenimiento_pieza.dart';

class Mantenimiento {
  int idMantenimiento;
  String fecha;
  int? km;
  double? costeTotal;
  String? observaciones;
  List<MantenimientoPieza>? piezas;

  Mantenimiento({
    required this.idMantenimiento,
    required this.fecha,
    this.km,
    this.costeTotal,
    this.observaciones,
    this.piezas,
  });

  factory Mantenimiento.fromJson(Map<String, dynamic> json) =>
      Mantenimiento(
        idMantenimiento: json["idMantenimiento"],
        fecha: json["fecha"] ?? "",
        km: json["km"],
        costeTotal: (json["costeTotal"] as num?)?.toDouble(),
        observaciones: json["observaciones"],
        piezas: json["piezas"] != null
            ? List<MantenimientoPieza>.from(
                json["piezas"].map((x) => MantenimientoPieza.fromJson(x)))
            : null,
      );

  Map<String, dynamic> toJson() => {
        "idMantenimiento": idMantenimiento,
        "fecha": fecha,
        "km": km,
        "costeTotal": costeTotal,
        "observaciones": observaciones,
        "piezas": piezas?.map((x) => x.toJson()).toList(),
      };
}
