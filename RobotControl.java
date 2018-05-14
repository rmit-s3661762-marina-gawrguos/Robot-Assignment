import javax.swing.JOptionPane;

class RobotControl {
	private Robot r;
	public static StringBuilder sb;

	public RobotControl(Robot r) {
		this.r = r;
		if (Robot.assessment == true) {

			r.speedUp(5);
		}
	}

	public void control(int barHeights[], int blockHeights[]) {

		run(barHeights, blockHeights);

	}

	public void run(int barHeights[], int blockHeights[]) {

		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		int currentBar = 0;
		int targetCol1Ht = 0;
		int targetCol2Ht = 0;
		int sourceHt = 0;
		int clearence = 0;
		for (int i = 0; i < blockHeights.length; i++) {
			sourceHt = sourceHt + blockHeights[i];

		}

		for (int i = 0; i < blockHeights.length; i++) {
			//determining the topmost block
			int blockHt = blockHeights[blockHeights.length - 1 - i]; 

			if (blockHt == 3) {

				for (int x = 0; x < barHeights.length - 1; x++) {

					// determining what clearence should by comparing it to barHeights[i],targetCol1Ht and targetCol2Ht.
					if ((sourceHt > barHeights[x]) && (sourceHt > targetCol1Ht) && (sourceHt > targetCol2Ht)) {
						clearence = sourceHt;
					} else if ((targetCol1Ht > sourceHt) && (targetCol1Ht > barHeights[x])
							&& (targetCol1Ht > targetCol2Ht)) {
						clearence = targetCol1Ht;

					} else if ((targetCol2Ht > sourceHt) && (targetCol2Ht > barHeights[x])
							&& (targetCol2Ht > targetCol1Ht)) {
						clearence = targetCol2Ht;
					}
				}
// assigning the value of barHeihgts[x] to clearence as long as x is less than the length of the bar Height - 1
				for (int x = 0; x < barHeights.length - 1; x++) {
					if (barHeights[x] > clearence) {
						clearence = barHeights[x];
					}
				}
			}
			// updating clearence to be sourceHt for stages 5 and 8
			clearence = sourceHt;
			while (h < clearence + 1) {

				// Raising 1
				r.up();

				// Current height of arm1 being incremented by 1
				h++;
			}

			System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			
			int extendAmt = 10;

			// Bring arm 2 to column 10
			while (w < extendAmt) {
				// moving 1 step horizontally
				r.extend();

				// Current width of arm2 being incremented by 1
				w++;
			}

			System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			

			d = lowerThirdArm(h, d, sourceHt);

			// picking the topmost block
			r.pick();

			// When you pick the top block height of source decreases
			sourceHt = sourceHt - blockHt;
			if (blockHt == 1) {
				for (int x = 0; x < barHeights.length - 1; x++) {
					if ((sourceHt > barHeights[x]) && (sourceHt > targetCol1Ht) && (sourceHt > targetCol2Ht)) {
						clearence = sourceHt;
					} else if ((targetCol1Ht > sourceHt) && (targetCol1Ht > barHeights[x])
							&& (targetCol1Ht > targetCol2Ht)) {
						clearence = targetCol1Ht;

					} else if ((targetCol2Ht > sourceHt) && (targetCol2Ht > barHeights[x])
							&& (targetCol2Ht > targetCol1Ht)) {
						clearence = targetCol2Ht;
					}
				}

				for (int x = 0; x < barHeights.length - 1; x++) {
					if (barHeights[x] > clearence) {
						clearence = barHeights[x];
					}
				}
			}

			if (blockHt == 2) {
				for (int x = 0; x < barHeights.length - 1; x++) {

					if ((sourceHt > barHeights[x]) && (sourceHt > targetCol2Ht)) {
						clearence = sourceHt;
					}

					else if ((targetCol2Ht > sourceHt) && (targetCol2Ht > barHeights[x])) {
						clearence = targetCol2Ht;
					}
				}

				for (int x = 0; x < barHeights.length - 1; x++) {
					if (barHeights[x] > clearence) {
						clearence = barHeights[x];
					}
				}
			}

			if (blockHt == 3) {
				clearence = 0;
				if (currentBar == 0) {
					for (int x = 0; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 0; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 1) {

					for (int x = 1; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 1; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 2) {
					for (int x = 2; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 2; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 3) {
					for (int x = 3; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 3; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
			}

			// raising third arm all the way until d becomes 0
			d = raiseThirdArm(h, d, clearence, blockHt);

			System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			int contractAmt = contractAmount(currentBar, blockHt);

			while (contractAmt > 0) { // contracting as long as contractAmt is
										// greater than 0
				r.contract();
				contractAmt--;
				w--;

			}

			System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

	/* For blockHt's 1,2 and 3, the below nested if statements are implemented as long as the conditions are satisfied.*/

			if (blockHt == 3) {
				while ((h - 1) - d - blockHt > barHeights[currentBar]) {
					r.lower();
					d++;

				}

				r.drop();
			} else if (blockHt == 1) { 
				while ((h - 1) - d - blockHt > targetCol1Ht) {
					r.lower();
					d++;

				}
				r.drop();
				targetCol1Ht = targetCol1Ht + blockHt;
			}

			else if (blockHt == 2) {
				while ((h - 1) - d - blockHt > targetCol2Ht) {
					r.lower();
					d++;
				}
				r.drop();
				targetCol2Ht = targetCol2Ht + blockHt;

			}

			System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			if (blockHt == 3) {
				// The height of currentBar increases by block just placed
				barHeights[currentBar] += blockHt;
				currentBar++;
			}
//updating clearence again after the block is dropped and placed either on a bar,targetCol1 or targetCol2
			if (blockHt == 1) {
				for (int x = 0; x < barHeights.length - 1; x++) {
					if ((sourceHt > barHeights[x]) && (sourceHt > targetCol1Ht) && (sourceHt > targetCol2Ht)) {
						clearence = sourceHt;
					} else if ((targetCol1Ht > sourceHt) && (targetCol1Ht > barHeights[x])
							&& (targetCol1Ht > targetCol2Ht)) {
						clearence = targetCol1Ht;

					} else if ((targetCol2Ht > sourceHt) && (targetCol2Ht > barHeights[x])
							&& (targetCol2Ht > targetCol1Ht)) {
						clearence = targetCol2Ht;
					}
				}

				for (int x = 0; x < barHeights.length - 1; x++) {
					if (barHeights[x] > clearence) {
						clearence = barHeights[x];
					}
				}
			}

			if (blockHt == 2) {
				for (int x = 0; x < barHeights.length - 1; x++) {

					if ((sourceHt > barHeights[x]) && (sourceHt > targetCol2Ht)) {
						clearence = sourceHt;
					}

					else if ((targetCol2Ht > sourceHt) && (targetCol2Ht > barHeights[x])) {
						clearence = targetCol2Ht;
					}
				}

				for (int x = 0; x < barHeights.length - 1; x++) {
					if (barHeights[x] > clearence) {
						clearence = barHeights[x];
					}
				}
			}

			if (blockHt == 3) {
				clearence = 0;
				if (currentBar == 0) {
					for (int x = 0; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 0; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 1) {

					for (int x = 1; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 1; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 2) {
					for (int x = 2; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 2; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
				if (currentBar == 3) {
					for (int x = 3; x < barHeights.length - 1; x++) {

						if ((sourceHt > barHeights[x])) {
							clearence = sourceHt;

						}

					}

					for (int x = 3; x < barHeights.length - 1; x++) {
						if (barHeights[x] > clearence) {
							clearence = barHeights[x];
						}
					}
				}
			}

			// raising the third arm

			while (h - d - 1 < clearence) {

				r.raise();
				d--;

			}

			System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

		}

	}

	/*method that lowering third arm - returns d as long as the subtraction of the h arm and d arm is greater than the (sourceHt + 1) */
	
	private int lowerThirdArm(int h, int d, int sourceHt) {
		while (h - d > sourceHt + 1) {
			// lowering third arm
			r.lower();

			// current depth of arm 3 being incremented
			d++;
		}
		return d;
	}
	/*method that raises third arm - returns d as long as the below while loop statement is true */
	private int raiseThirdArm(int h, int d, int clearence, int blockHt) {
		while (h - (d + blockHt) - 1 < clearence) {
			r.raise();
			d--;
		}
		return d;
	}
/* method that contracts by a certain amount depending on the current Bar number*/
	private int contractAmount(int currentBar, int blockHt) {
		int contractAmt = 7; // contracts by 7 if current Bar number is 0
		if (currentBar == 1) { // contracts by 6 if current Bar number is 1
			contractAmt = 6;
		} else if (currentBar == 2) { // contracts by 5 if current bar
										// number is 2
			contractAmt = 5;
		} else if (currentBar == 3) { // contracts by 4 if current bar
										// number is 3
			contractAmt = 4;
		}
		if (blockHt == 1) { // stage c: robot contracts by 9 if block height
							// is 1
			contractAmt = 9;

		} else if (blockHt == 2) {// stage c: robot contracts by 8 if block
									// height is 2
			contractAmt = 8;

		}
		return contractAmt;
	}

}
