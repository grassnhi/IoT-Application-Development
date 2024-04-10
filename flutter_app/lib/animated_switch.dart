import 'package:flutter/material.dart';

class AnimatedSwitch extends StatelessWidget {
  final List<bool> isToggled;
  final int index;
  final void Function() onTap;
  final String label;
  final String iconPath;

  const AnimatedSwitch({
    super.key,
    required this.isToggled,
    required this.index,
    required this.onTap,
    required this.label,
    required this.iconPath,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.all(8.0),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(15),
          border: Border.all(
            width: 1,
            color: Theme.of(context).primaryColor,
          ),
        ),
        child: Row(
          children: [
            Image.asset(
              iconPath,
              height: 24,
              width: 24,
            ),
            const SizedBox(width: 8),
            Text(
              label,
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(width: 8),
            Container(
              height: 28,
              width: 46,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(15),
                border: Border.all(
                  width: 1,
                  color: Theme.of(context).primaryColor,
                ),
              ),
              child: Stack(
                children: [
                  AnimatedCrossFade(
                    firstChild: Container(
                      height: 30,
                      width: 46,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(18),
                        color: Colors.transparent,
                      ),
                    ),
                    secondChild: Container(
                      height: 30,
                      width: 46,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(18),
                        color: Theme.of(context).primaryColor,
                      ),
                    ),
                    crossFadeState: isToggled[index]
                        ? CrossFadeState.showSecond
                        : CrossFadeState.showFirst,
                    duration: const Duration(milliseconds: 200),
                  ),
                  AnimatedAlign(
                    duration: const Duration(milliseconds: 300),
                    alignment: isToggled[index]
                        ? Alignment.centerRight
                        : Alignment.centerLeft,
                    child: Container(
                      margin: const EdgeInsets.symmetric(
                        horizontal: 4,
                      ),
                      height: 42 * 0.5,
                      width: 42 * 0.5,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: isToggled[index]
                            ? Colors.white
                            : Theme.of(context).primaryColor,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
