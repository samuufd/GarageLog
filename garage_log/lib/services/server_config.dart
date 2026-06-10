import 'package:shared_preferences/shared_preferences.dart';

class ServerConfig {
  ServerConfig._();

  static const _key = 'server_ip';
  static const _defaultIp = '192.168.1.19';
  static const _port = '8080';

  static String _ip = _defaultIp;

  static String get baseUrl => 'http://$_ip:$_port/garagelog';

  static String get ip => _ip;

  static Future<void> init() async {
    final prefs = await SharedPreferences.getInstance();
    _ip = prefs.getString(_key) ?? _defaultIp;
  }

  static Future<void> saveIp(String ip) async {
    _ip = ip;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_key, ip);
  }

  static bool get hasCustomIp => _ip != _defaultIp;

}
