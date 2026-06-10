import 'pieza.dart';

class MantenimientoPieza {
  int idMantenimientoPieza;
  int cantidad;
  double? precioUnitario;
  String? observaciones;
  Pieza? pieza;

  MantenimientoPieza({
    required this.idMantenimientoPieza,
    required this.cantidad,
    this.precioUnitario,
    this.observaciones,
    this.pieza,
  });

  factory MantenimientoPieza.fromJson(Map<String, dynamic> json) =>
      MantenimientoPieza(
        idMantenimientoPieza: json["idMantenimientoPieza"],
        cantidad: json["cantidad"] ?? 0,
        precioUnitario: (json["precioUnitario"] as num?)?.toDouble(),
        observaciones: json["observaciones"],
        pieza: json["pieza"] != null
            ? Pieza.fromJson(json["pieza"])
            : null,
      );

  Map<String, dynamic> toJson() => {
        "idMantenimientoPieza": idMantenimientoPieza,
        "cantidad": cantidad,
        "precioUnitario": precioUnitario,
        "observaciones": observaciones,
        "pieza": pieza?.toJson(),
      };

  double get subtotal => (precioUnitario ?? 0) * cantidad;
}
