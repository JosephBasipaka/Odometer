package com.learning.hello.controller;

import Odometer.exceptions.NonAscendingReadingException;
import Odometer.exceptions.ReadingSizeMismatchException;

	public class OdometerController {
		
		private static final String DIGITS = "123456789";
		
		private int reading;
		
		private static int getMinReading(int size) {
			return Integer.valueOf(DIGITS.substring(0, size));
		}
		
		private static int getMaxReading(int size) {
			return Integer.valueOf(DIGITS.substring(DIGITS.length() - size, DIGITS.length()));
		}
		
		private static int getSize(int reading) {
			return String.valueOf(reading).length();
		}
		
		public OdometerController(int size) {
			reading = getMinReading(size);
		}
		
		public OdometerController(OdometerController copy) {
			reading = copy.reading;
		}
		
		public int getReading() {
			return reading;
		}
		
		public void setReading(int reading) throws NonAscendingReadingException,ReadingSizeMismatchException {
			if (!isAscending(reading))
				throw new NonAscendingReadingException("Reading has to have all its digits in strictly ascending order. (got: " + reading + ")");
			else if (getSize(reading) != getSize(this.reading))
				throw new ReadingSizeMismatchException("Reading has to be the same size as the previous reading. (Existing: " + this.reading + " got : " + reading + ")");
			else
				this.reading = reading;
		}
		
		@Override
		public String toString() {
			return "(" + reading + ")";
		}
		
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof OdometerController))
				return false;
			OdometerController otherOdo = (OdometerController) other;
			return otherOdo.reading == this.reading;
		}
		
		public static boolean isAscending(int reading) {
			if (reading < 10)
				return true;
			if(reading % 10 <= (reading / 10) % 10)
				return false;
			return isAscending(reading / 10);
		}
		
		
		public void incrementReading() {
			do {
				if (reading == getMaxReading(getSize(reading)))
					reading = getMinReading(getSize(reading));
				else
					reading++;
			} while (!isAscending(reading));
		}
		
		public OdometerController nextReading() {
			OdometerController temp = new OdometerController(this);
			temp.incrementReading();
			return temp;
		}
		
		public void decrementReading() {
			do {
				if (reading == getMinReading(getSize(reading)))
					reading = getMaxReading(getSize(reading));
				else
					reading--;
			} while (!isAscending(reading));
		}
		
		public void reset() {
			this.reading = getMinReading(getSize(this.reading));
		}
		
		public int getSize() {
			return getSize(this.reading);
		}
	}

