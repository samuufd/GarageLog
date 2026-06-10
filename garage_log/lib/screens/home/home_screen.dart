import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:garage_log/core/theme/app_theme.dart';
import 'package:garage_log/providers/theme_provider.dart';
import 'package:garage_log/routes/routes_garagelog.dart';
import 'package:garage_log/screens/busqueda/busqueda_delegate.dart';
import 'package:garage_log/services/server_config.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  static bool _dialogShown = false;

  Future<void> _showServerConfigDialog(BuildContext context) async {
    final controller = TextEditingController(text: ServerConfig.ip);
    final result = await showDialog<String>(
      context: context,
      barrierDismissible: false,
      builder: (ctx) => AlertDialog(
        title: const Text('Configurar servidor'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text(
              'Introduce la IP del servidor:',
              style: TextStyle(fontSize: 14),
            ),
            const SizedBox(height: 12),
            TextField(
              controller: controller,
              decoration: const InputDecoration(
                hintText: '192.168.1.19',
                labelText: 'Dirección IP',
              ),
              keyboardType: TextInputType.number,
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(ctx).pop(),
            child: const Text('Omitir'),
          ),
          FilledButton(
            onPressed: () {
              final ip = controller.text.trim();
              if (ip.isNotEmpty) {
                Navigator.of(ctx).pop(ip);
              }
            },
            child: const Text('Guardar'),
          ),
        ],
      ),
    );
    if (result != null && result.isNotEmpty && context.mounted) {
      await ServerConfig.saveIp(result);
    }
  }

  @override
  Widget build(BuildContext context) {
    if (!_dialogShown && !ServerConfig.hasCustomIp) {
      _dialogShown = true;
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (context.mounted) _showServerConfigDialog(context);
      });
    }

    final isDark = Theme.of(context).brightness == Brightness.dark;
    final textSecondary =
        isDark ? AppTheme.darkTextSecondary : AppTheme.textSecondary;

    return Scaffold(
      drawer: _buildDrawer(context),
      appBar: AppBar(
        title: const Text('GarageLog'),
        bottom: PreferredSize(
          preferredSize: const Size.fromHeight(2),
          child: Container(
            color: AppTheme.accent.withValues(alpha: 0.4),
            height: 2,
          ),
        ),
      ),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Image.asset(
                'assets/images/logo.png',
                width: 150,
                height: 150,
              ),
              const SizedBox(height: 20),
              Text(
                'GarageLog',
                style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                      letterSpacing: 1.2,
                    ),
              ),
              const SizedBox(height: 8),
              Container(
                width: 32,
                height: 3,
                decoration: BoxDecoration(
                  color: AppTheme.accent.withValues(alpha: 0.5),
                  borderRadius: BorderRadius.circular(2),
                ),
              ),
              const SizedBox(height: 12),
              Text(
                'Control de mantenimiento\npara tu vehículo',
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                      color: textSecondary,
                      height: 1.6,
                      letterSpacing: 0.2,
                    ),
              ),
              const SizedBox(height: 32),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildDrawer(BuildContext context) {
    final themeProvider = Provider.of<ThemeProvider>(context);
    final isDark = themeProvider.isDark;

    return Drawer(
      width: 260,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            width: double.infinity,
            padding: EdgeInsets.only(
              top: MediaQuery.of(context).padding.top + 16,
              left: 20,
              right: 20,
              bottom: 16,
            ),
            decoration: BoxDecoration(
              color: isDark ? AppTheme.darkSurface : const Color(0xFF2C2C2C),
              border: Border(
                bottom: BorderSide(
                  color: AppTheme.accent.withValues(alpha: 0.3),
                  width: 1.5,
                ),
              ),
            ),
            child: Row(
              children: [
                Image.asset(
                  'assets/images/logo.png',
                  width: 56,
                  height: 56,
                ),
                const SizedBox(width: 14),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'GarageLog',
                        style: TextStyle(
                          fontSize: 19,
                          fontWeight: FontWeight.w700,
                          letterSpacing: 0.8,
                          color: isDark
                              ? AppTheme.darkTextPrimary
                              : Colors.white,
                        ),
                      ),
                      const SizedBox(height: 2),
                      Text(
                        'Control de mantenimiento',
                        style: TextStyle(
                          fontSize: 12,
                          color: isDark
                              ? AppTheme.darkTextTertiary
                              : Colors.white60,
                          letterSpacing: 0.3,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(height: 4),
          _DrawerItem(
            icon: Icons.format_list_bulleted_rounded,
            label: 'Listado completo',
            onTap: () {
              Navigator.of(context).pop();
              Navigator.of(context).pushNamed(RoutesGaragelog.listado);
            },
          ),
          _DrawerItem(
            icon: Icons.search_rounded,
            label: 'Búsqueda',
            onTap: () {
              Navigator.of(context).pop();
              showSearch(
                context: context,
                delegate: BusquedaDelegate(),
              );
            },
          ),
          const Spacer(),
          const Divider(indent: 16, endIndent: 16, height: 1),
          SwitchListTile(
            dense: true,
            secondary: AnimatedSwitcher(
              duration: const Duration(milliseconds: 250),
              child: Icon(
                key: ValueKey(isDark),
                isDark ? Icons.dark_mode_rounded : Icons.light_mode_rounded,
                color: isDark ? AppTheme.accent : AppTheme.primary,
                size: 22,
              ),
            ),
            title: AnimatedSwitcher(
              duration: const Duration(milliseconds: 250),
              child: Text(
                key: ValueKey(isDark),
                isDark ? 'Modo oscuro' : 'Modo claro',
                style: TextStyle(
                  color:
                      isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary,
                  fontWeight: FontWeight.w500,
                  fontSize: 15,
                ),
              ),
            ),
            value: isDark,
            activeTrackColor: AppTheme.accent.withValues(alpha: 0.4),
            activeThumbColor: AppTheme.accent,
            onChanged: (_) => themeProvider.toggleTheme(),
          ),
          const Divider(indent: 16, endIndent: 16, height: 1),
          _DrawerItem(
            icon: Icons.settings_rounded,
            label: 'Configurar servidor',
            onTap: () {
              Navigator.of(context).pop();
              _showServerConfigDialog(context);
            },
          ),
          const Divider(indent: 16, endIndent: 16, height: 1),
          _DrawerItem(
            icon: Icons.exit_to_app_rounded,
            label: 'Salir',
            iconColor: AppTheme.error,
            textColor: AppTheme.error,
            onTap: () {
              Navigator.of(context).pop();
              SystemNavigator.pop();
            },
          ),
          const SizedBox(height: 12),
        ],
      ),
    );
  }
}

class _DrawerItem extends StatelessWidget {
  final IconData icon;
  final String label;
  final VoidCallback onTap;
  final Color? iconColor;
  final Color? textColor;

  const _DrawerItem({
    required this.icon,
    required this.label,
    required this.onTap,
    this.iconColor,
    this.textColor,
  });

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;
    final defaultIcon = AppTheme.accent.withValues(alpha: 0.75);
    final defaultText =
        isDark ? AppTheme.darkTextPrimary : AppTheme.textPrimary;

    return ListTile(
      leading: Icon(icon, color: iconColor ?? defaultIcon, size: 22),
      title: Text(
        label,
        style: TextStyle(
          color: textColor ?? defaultText,
          fontWeight: FontWeight.w500,
          fontSize: 15,
        ),
      ),
      onTap: onTap,
      dense: true,
    );
  }
}
