import 'package:flutter/material.dart';
import 'package:home/temperature.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool isLedOn = false;
  bool isPumpOn = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.indigo.shade50,
      body: SafeArea(
        child: Container(
          margin: const EdgeInsets.only(top: 18, left: 24, right: 24),
          child: Column(
            children: [
              const Row(
                children: [
                  Text(
                    'IoT',
                    style: TextStyle(
                      fontSize: 18,
                      color: Colors.indigo,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  RotatedBox(
                    quarterTurns: 135,
                    child: Icon(
                      Icons.bar_chart_rounded,
                      size: 28,
                      color: Colors.indigo,
                    ),
                  )
                ],
              ),
              Expanded(
                child: ListView(
                  physics: const BouncingScrollPhysics(),
                  children: [
                    const SizedBox(
                      height: 32,
                    ),
                    Center(
                      child: Image.asset(
                        'assets/images/banner.png',
                        scale: 1.2,
                      ),
                    ),
                    const SizedBox(
                      height: 16,
                    ),
                    const Center(
                      child: Text(
                        'Smart farm',
                        style: TextStyle(
                          fontSize: 32,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 48,
                    ),
                    const Text(
                      'SERVICE',
                      style: TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(
                      height: 16,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: GestureDetector(
                            onTap: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) {
                                    return const TemperaturePage();
                                  },
                                ),
                              );
                            },
                            child: _cardMenu(
                              title: 'Temperature',
                              icon: 'assets/images/temperature.png',
                              color: Color.fromARGB(255, 37, 187, 75),
                              fontColor: Colors.white,
                              value: '29°C',
                            ),
                          ),
                        ),

                        const SizedBox(width: 16), // Add distance
                        Expanded(
                          child: _cardMenu(
                            title: 'Humidity',
                            icon: 'assets/images/water.png',
                            color: Color.fromARGB(255, 37, 187, 75),
                            fontColor: Colors.white,
                            value: '45%',
                          ),
                        ),

                        const SizedBox(width: 16), // Add distance
                        Expanded(
                          child: _cardMenu(
                            title: 'Lighting',
                            icon: 'assets/images/sun.png',
                            color: Color.fromARGB(255, 37, 187, 75),
                            fontColor: Colors.white,
                            fit: BoxFit.contain,
                            value: '80%',
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 24), // Add spacing
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        OutlinedButton(
                          onPressed: () {
                            setState(() {
                              isLedOn = !isLedOn;
                            });
                          },
                          style: ButtonStyle(
                            side: MaterialStateProperty.resolveWith<BorderSide>(
                                (Set<MaterialState> states) {
                              return BorderSide(
                                  color: isLedOn ? Colors.green : Colors.grey, width: 2);
                            }),
                            shape: MaterialStateProperty.resolveWith<OutlinedBorder>(
                                (Set<MaterialState> states) {
                              return RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10.0),
                              );
                            }),
                            padding: MaterialStateProperty.all<EdgeInsetsGeometry>(
                                const EdgeInsets.symmetric(vertical: 10, horizontal: 20)),
                          ),
                          child: Text(
                            isLedOn ? 'LED ON' : 'LED OFF',
                            style: TextStyle(
                              color: isLedOn ? Colors.green : Colors.grey,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        OutlinedButton(
                          onPressed: () {
                            setState(() {
                              isPumpOn = !isPumpOn;
                            });
                          },
                          style: ButtonStyle(
                            side: MaterialStateProperty.resolveWith<BorderSide>(
                                (Set<MaterialState> states) {
                              return BorderSide(
                                  color: isPumpOn ? Colors.green : Colors.grey, width: 2);
                            }),
                            shape: MaterialStateProperty.resolveWith<OutlinedBorder>(
                                (Set<MaterialState> states) {
                              return RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10.0),
                              );
                            }),
                            padding: MaterialStateProperty.all<EdgeInsetsGeometry>(
                                const EdgeInsets.symmetric(vertical: 10, horizontal: 20)),
                          ),
                          child: Text(
                            isPumpOn ? 'PUMP ON' : 'PUMP OFF',
                            style: TextStyle(
                              color: isPumpOn ? Colors.green : Colors.grey,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget _cardMenu({
    required String title,
    required String icon,
    BoxFit fit = BoxFit.contain,
    String value = '',
    VoidCallback? onTap,
    Color color = Colors.white,
    Color fontColor = Colors.grey,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.circular(24),
      ),
      child: Column(
        children: [
          Image.asset(
            icon,
            fit: BoxFit.scaleDown,
          ),
          const SizedBox(
            height: 10,
          ),
          Text(
            title,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: fontColor,
            ),
          ),
          const SizedBox(
            height: 8,
          ),
          Text(
            value,
            style: const TextStyle(
              color: Colors.black,
              fontSize: 16,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }
}
