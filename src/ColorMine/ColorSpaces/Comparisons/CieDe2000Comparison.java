package ColorMine.ColorSpaces.Comparisons;

import java.util.ArrayList;
import flanagan.complex.Complex;



public class CieDe2000Comparison{




	    /// <summary>
	    /// Implements the DE2000 method of delta-e: http://en.wikipedia.org/wiki/Color_difference#CIEDE2000
	    /// Correct implementation provided courtesy of Jonathan Hofinger, jaytar42
		///	ported to Java K0Gs March 17 2015
	    /// </summary>
	   // public class CieDe2000Comparison : IColorSpaceComparison
	    //{
	        /// <summary>
	        /// Calculates the DE2000 delta-e value: http://en.wikipedia.org/wiki/Color_difference#CIEDE2000
	        /// Correct implementation provided courtesy of Jonathan Hofinger, jaytar42
	        /// </summary>
	        public double Compare(ArrayList lab1, ArrayList lab2)
	        {
	            //Set weighting factors to 1
	            double k_L = 1.0d;
	            double k_C = 1.0d;
	            double k_H = 1.0d;


	            //Change Color Space to L*a*b:
	            //Lab lab1 = c1.To<Lab>();
	            //Lab lab2 = c2.To<Lab>();
	            

	            //Calculate Cprime1, Cprime2, Cabbar
	            Complex c_star_1_ab = Complex.sqrt(new Complex((double) lab1.get(1) * (double) lab1.get(1) + (double) lab1.get(2) * (double) lab1.get(2)));
	            Complex c_star_2_ab = Complex.sqrt(new Complex((double) lab2.get(1) * (double) lab2.get(1) + (double) lab2.get(2) * (double) lab2.get(2)));
	            
	            double c_star_average_ab = (c_star_1_ab.getReal() + c_star_2_ab.getReal()) / 2.0;

	            double c_star_average_ab_pot7 = c_star_average_ab * c_star_average_ab * c_star_average_ab;
	            c_star_average_ab_pot7 *= c_star_average_ab_pot7 * c_star_average_ab;
	            	
	            Complex forG = Complex.sqrt(new Complex(c_star_average_ab_pot7 / (c_star_average_ab_pot7 + 610351562)));
	            double G = 0.5d * (1 - forG.getReal()); //25^7
	            double a1_prime = (1 + G) * (double) lab1.get(1);
	            double a2_prime = (1 + G) * (double) lab2.get(1);

	            Complex C_prime_1 = Complex.sqrt(new Complex(a1_prime * a1_prime + (double) lab1.get(2) * (double) lab1.get(2)));
	            Complex C_prime_2 = Complex.sqrt(new Complex(a2_prime * a2_prime + (double)lab2.get(2) * (double) lab2.get(2)));
	            //Angles in Degree.
	            
	            double h_prime_1 = ((Math.atan2((double) lab1.get(2), a1_prime) * 180d / Math.PI) + 360) % 360d;
	            double h_prime_2 = ((Math.atan2((double) lab2.get(2), a2_prime) * 180d / Math.PI) + 360) % 360d;

	            double delta_L_prime = (double) lab2.get(0) - (double) lab1.get(0);
	            double delta_C_prime = C_prime_2.getReal() - C_prime_1.getReal();

	            double h_bar = Complex.abs(new Complex(h_prime_1 - h_prime_2));
	            double delta_h_prime;
	            if (C_prime_1.getReal() * C_prime_2.getReal() == 0) delta_h_prime = 0;
	            else
	            {
	                if (h_bar <= 180d)
	                {
	                    delta_h_prime = h_prime_2 - h_prime_1;
	                }
	                else if (h_bar > 180d && h_prime_2 <= h_prime_1)
	                {
	                    delta_h_prime = h_prime_2 - h_prime_1 + 360.0;
	                }
	                else
	                {
	                    delta_h_prime = h_prime_2 - h_prime_1 - 360.0;
	                }
	            }
	            Complex fordelta_H_prime = Complex.sqrt(new Complex(C_prime_1.getReal() * C_prime_2.getReal()));
	            
	            double delta_H_prime = 2 * fordelta_H_prime.getReal() * Math.sin(delta_h_prime * Math.PI / 360d);

	            // Calculate CIEDE2000
	            double L_prime_average = ((double) lab1.get(0) + (double) lab2.get(0)) / 2d;
	            double C_prime_average = (C_prime_1.getReal() + C_prime_2.getReal()) / 2d;

	            //Calculate h_prime_average

	            double h_prime_average;
	            if (C_prime_1.getReal() * C_prime_2.getReal() == 0) h_prime_average = 0;
	            else
	            {
	                if (h_bar <= 180d)
	                {
	                    h_prime_average = (h_prime_1 + h_prime_2) / 2;
	                }
	                else if (h_bar > 180d && (h_prime_1 + h_prime_2) < 360d)
	                {
	                    h_prime_average = (h_prime_1 + h_prime_2 + 360d) / 2;
	                }
	                else
	                {
	                    h_prime_average = (h_prime_1 + h_prime_2 - 360d) / 2;
	                }
	            }
	            double L_prime_average_minus_50_square = (L_prime_average - 50);
	            L_prime_average_minus_50_square *= L_prime_average_minus_50_square;
	            
	            Complex forS_L = Complex.sqrt(new Complex(20 + L_prime_average_minus_50_square));
	            
	            double S_L = 1 + ((.015d * L_prime_average_minus_50_square) / forS_L.getReal());
	            double S_C = 1 + .045d * C_prime_average;
	            double T = 1
	                - .17 * Complex.cos(new Complex(DegToRad(h_prime_average - 30))).getReal()
	                + .24 * Complex.cos(new Complex(DegToRad(h_prime_average * 2))).getReal()
	                + .32 * Complex.cos(new Complex(DegToRad(h_prime_average * 3 + 6))).getReal()
	                - .2 * Complex.cos(new Complex(DegToRad(h_prime_average * 4 - 63))).getReal();
	            double S_H = 1 + .015 * T * C_prime_average;
	            double h_prime_average_minus_275_div_25_square = (h_prime_average - 275) / (25);
	            h_prime_average_minus_275_div_25_square *= h_prime_average_minus_275_div_25_square;
	            double delta_theta = 30 * Complex.exp(new Complex(-h_prime_average_minus_275_div_25_square)).getReal();

	            double C_prime_average_pot_7 = C_prime_average * C_prime_average * C_prime_average;
	            C_prime_average_pot_7 *= C_prime_average_pot_7 * C_prime_average;
	            
	            Complex forR_C = Complex.sqrt(new Complex(C_prime_average_pot_7 / (C_prime_average_pot_7 + 610351562)));
	            
	            double R_C = 2 * forR_C.getReal();

	            double R_T = -Complex.sin(new Complex(DegToRad(2 * delta_theta))).getReal() * R_C;

	            double delta_L_prime_div_k_L_S_L = delta_L_prime / (S_L * k_L);
	            double delta_C_prime_div_k_C_S_C = delta_C_prime / (S_C * k_C);
	            double delta_H_prime_div_k_H_S_H = delta_H_prime / (S_H * k_H);

	            Complex CIEDE2000 = Complex.sqrt(new Complex(
	                delta_L_prime_div_k_L_S_L * delta_L_prime_div_k_L_S_L
	                + delta_C_prime_div_k_C_S_C * delta_C_prime_div_k_C_S_C
	                + delta_H_prime_div_k_H_S_H * delta_H_prime_div_k_H_S_H
	                + R_T * delta_C_prime_div_k_C_S_C * delta_H_prime_div_k_H_S_H
	                ));

	            return CIEDE2000.getReal();
	        }
	        private double DegToRad(double degrees)
	        {
	            return degrees * Math.PI / 180;
	        }
	    


	
	
}
