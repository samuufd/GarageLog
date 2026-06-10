class Pieza {
  int idPieza;
  String nombre;
  String? descripcion;
  double? precio;

  Pieza({
    required this.idPieza,
    required this.nombre,
    this.descripcion,
    this.precio,
  });

  factory Pieza.fromJson(Map<String, dynamic> json) => Pieza(
        idPieza: json["idPieza"],
        nombre: json["nombre"] ?? "",
        descripcion: json["descripcion"],
        precio: (json["precio"] as num?)?.toDouble(),
      );

  Map<String, dynamic> toJson() => {
        "idPieza": idPieza,
        "nombre": nombre,
        "descripcion": descripcion,
        "precio": precio,
      };
}
