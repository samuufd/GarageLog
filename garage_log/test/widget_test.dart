import 'package:flutter_test/flutter_test.dart';
import 'package:garage_log/providers/providers.dart';

void main() {
  testWidgets('App smoke test', (WidgetTester tester) async {
    await tester.pumpWidget(const Providers());
    expect(find.text('GarageLog'), findsOneWidget);
  });
}
