# CPU with Erosion program and test programs by Group 22
To run the programs go to 'CPUTopTester.scala' and uncomment the program corresponding to the following:
- Test of memory store and save: Line 33 - Test is successful if the two first output pixels are white
- Test of arithmetic and logic functions: Line 35 - Test is successful if the first output pixel is white
- Test of branch instrutions: Line 37 - Test is successful if the first output pixel is white
- Test of incrementers: Line 39 - No graphic indicator for test success. Inspect waveforms.
- Erosion program: Line 41
Afterwards, while in the root folder, run:
```
sbt "test:runMain CPUTopTester"
```