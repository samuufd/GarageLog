import 'package:flutter/material.dart';
import 'package:garage_log/screens/home/home_screen.dart';
import 'package:garage_log/screens/listado/listado_screen.dart';
import 'package:garage_log/screens/detalle/detalle_screen.dart';

class RoutesGaragelog {
  static const String home = "/";
  static const String listado = "/listado";
  static const String detalle = "/detalle";

  static final Map<String, WidgetBuilder> routes = {
    home: (_) => const HomeScreen(),
    listado: (_) => const ListadoScreen(),
    detalle: (_) => const DetalleScreen(),
  };
}
