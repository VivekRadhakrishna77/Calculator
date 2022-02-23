package com.vivekrad.calculator;

// Vivek Radhakrishna 2060838

import java.util.ArrayList;
import java.util.Scanner;


public class Calculator {
	
	static Calculator calculator = new Calculator();
	static Float memory, currentValue=(float)0;
	static ArrayList<Float> history = new ArrayList<Float>();
	static ArrayList<String> opStack = new ArrayList<String>();
	static ArrayList<Float> opQ = new ArrayList<Float>();
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		for(int k=0; k<20;k++) {
			String input = scanner.nextLine();
			if(input.equals("m")) {
				calculator.setMemoryValue(calculator.getCurrentValue());
				continue;
			}
			if(input.equals("mr")) {
				System.out.println(calculator.getMemoryValue());
				continue;
			}
			if(input.equals("h")) {
				calculator.history();
				continue;
			}
			if(input.equals("c")) {
				calculator.clearMemory();
				continue;
			}
			if(!input.isEmpty()) {
					
				float answer = calculator.evaluate(input);	
				currentValue = answer;
				calculator.addToHistory(calculator.getCurrentValue());
				if(answer == Float.MIN_VALUE) {
					System.out.println("Invalid Input");
					continue;
				} else {
					System.out.println(answer);
					continue;
				}
			}
			else {
				continue;
			}
			
			
		}
	
		scanner.close();
	}
	
	public float evaluate(String expression) {
		
		float m, bracket1=Float.MIN_VALUE, bracket2=Float.MIN_VALUE;
		float result = Float.MIN_VALUE;
		String[] parts = expression.split("\\s");
		
		
		if(parts.length == 7 && parts[0].contains("(")) {
			//first brackets contents = parts0-2
			//middle operand = parts[3]
			//second brackets contents = 4-6
			
			//-----------Bracket 1-----------
			String num_1 = parts[0];
			num_1 = num_1.replace("(", "");
			String operator_1 = parts[1];
			String num_2 = parts[2];
			num_2 = num_2.replace(")", "");
			
			//-------------------------------
			String operator_2 = parts[3];
			
			
			//-----------Bracket 2-----------
			String num_3 = parts[4];
			num_3 = num_3.replace("(", "");
			String operator_3 = parts[5];
			String num_4 = parts[6];
			num_4 = num_4.replace(")", "");
			
			if(num_1.matches("-?\\d*\\.?\\d+") && operator_1.matches("\\+|\\-|\\*|\\/") && num_2.matches("-?\\d*\\.?\\d+")){
				float x = Float.parseFloat(num_1);
				float y = Float.parseFloat(num_2);
				
				if(operator_1.equals("+")) {
					bracket1 = calculator.add(x,y);
				
				} else if(operator_1.equals("-")) {
					bracket1 = calculator.subtract(x,y);
				
				} else if(operator_1.equals("*")) {
					bracket1 = calculator.multiply(x,y);
					
				
				} else if(operator_1.equals("/")) {
					bracket1 = calculator.divide(x,y);
				
				} else {
					
					return Float.MIN_VALUE;
				}
			} else {
				
				return Float.MIN_VALUE;
			}
			
			if(num_3.matches("-?\\d*\\.?\\d+") && operator_3.matches("\\+|\\-|\\*|\\/") && num_4.matches("-?\\d*\\.?\\d+")){
				float x = Float.parseFloat(num_3);
				float y = Float.parseFloat(num_4);
				
				if(operator_3.equals("+")) {
					bracket2 = calculator.add(x,y);
				
				} else if(operator_3.equals("-")) {
					bracket2 = calculator.subtract(x,y);
				
				} else if(operator_3.equals("*")) {
					bracket2 = calculator.multiply(x,y);
					
				
				} else if(operator_3.equals("/")) {
					bracket2 = calculator.divide(x,y);
				
				} else {
					
					return Float.MIN_VALUE;
				}
			}
				else {
					
					return Float.MIN_VALUE;
				}
			
			
			if(operator_2.matches("\\+|\\-|\\*|\\/")){
				if(operator_2.equals("+")) {
					result = calculator.add(bracket1,bracket2);
				
				} else if(operator_2.equals("-")) {
					result = calculator.subtract(bracket1,bracket2);
				
				} else if(operator_2.equals("*")) {
					result = calculator.multiply(bracket1,bracket2);
					
				
				} else if(operator_2.equals("/")) {
					result = calculator.divide(bracket1,bracket2);
				
				} else {
					return Float.MIN_VALUE;
				}
			}
			
			return result;
			}
		
		

		
		
		else if(parts.length == 3) {
			String num_1 = parts[0];
			String operator = parts[1];
			String num_2 = parts[2];
		
			if(num_1.matches("-?\\d*\\.?\\d+") && operator.matches("\\+|\\-|\\*|\\/") && num_2.matches("-?\\d*\\.?\\d+")){
				float x = Float.parseFloat(num_1);
				float y = Float.parseFloat(num_2);
				if(operator.equals("+")) {
					result = calculator.add(x,y);
				
				} else if(operator.equals("-")) {
					result = calculator.subtract(x,y);
				
				} else if(operator.equals("*")) {
					result = calculator.multiply(x,y);
					
				
				} else if(operator.equals("/")) {
					result = calculator.divide(x,y);
				
				}
			} 
			
		}
		
		else if(parts.length == 2) {
			String operator = parts[0];
			String num = parts[1];
			if(operator.matches("\\+|\\-|\\*|\\/") && num.matches("-?\\d*\\.?\\d+")) {
				if(memory==null) {
					currentValue=(float)0;
					return Float.MIN_VALUE;
					
				}else {
					m = getMemoryValue();
				}
				
				float x = m;
				float y = Float.parseFloat(num);
				if(operator.equals("+")) {
					result = calculator.add(x,y);
				
				} else if(operator.equals("-")) {
					result = calculator.subtract(x,y);
					
				
				} else if(operator.equals("*")) {
					result = calculator.multiply(x,y);
					
				
				} else if(operator.equals("/")) {
					result = calculator.divide(x,y);
				
				}
			}
			
		}
		else {
			
			for(int i=0; i<parts.length;i++) {
				
					if(parts[i].matches("-?\\d*\\.?\\d+")) {
						
					opQ.add(Float.parseFloat(parts[i]));
					
					continue;
					
				}
					else if(parts[i].matches("\\+|\\-|\\*|\\/")) {
						
						if(opStack.isEmpty()) {
							opStack.add(parts[i]);
						} 
						else {
							if(parts[i].equals("*")) {
								if(opStack.get(opStack.size()-1).equals("/")) {
									
									opStack.remove(opStack.size()-1);
									opStack.add(parts[i]);
									
									float y = opQ.get(opQ.size()-1);
									opQ.remove(opQ.size()-1);
									float x = opQ.get(opQ.size()-1);
									opQ.remove(opQ.size()-1);
									result = calculator.divide(x,y);
									opQ.add(result);
								} else {
									opStack.add(parts[i]);
								}
						
							}
							else if(parts[i].equals("/")) {
								opStack.add(parts[i]);
							}
							else if(parts[i].equals("+") || parts[i].equals("-")) {
								
									if(opStack.get(opStack.size()-1).equals("*") || opStack.get(opStack.size()-1).equals("/")){
										
										String operator = opStack.get(opStack.size()-1);
										opStack.remove(opStack.size()-1);
										
										float y = opQ.get(opQ.size()-1);
										opQ.remove(opQ.size()-1);
										float x = opQ.get(opQ.size()-1);
										opQ.remove(opQ.size()-1);
										
										
										if(operator.equals("*")) {
											result = calculator.multiply(x,y);
											opQ.add(result);
											
											
											if (!opStack.isEmpty()) {
												if ((opStack.get(opStack.size()-1).equals("-"))) {
													opStack.remove(opStack.size()-1);
													
													y = opQ.get(opQ.size()-1);
													opQ.remove(opQ.size()-1);
													x = opQ.get(opQ.size()-1);
													opQ.remove(opQ.size()-1);
													
													result = calculator.subtract(x, y);
													opQ.add(result);
												} 
											}
											
										} else if(operator.equals("/")) {
											result = calculator.divide(x,y);
											opQ.add(result);
											
											if (!opStack.isEmpty()) {
												if ((opStack.get(opStack.size()-1).equals("*"))) {
													opStack.remove(opStack.size()-1);
													
													y = opQ.get(opQ.size()-1);
													opQ.remove(opQ.size()-1);
													x = opQ.get(opQ.size()-1);
													opQ.remove(opQ.size()-1);
													
													result = calculator.multiply(x, y);
													opQ.add(result);
												} 
											}
										}
										
										else if(opStack.get(opStack.size()-1).equals("-")) {
											opStack.remove(opStack.size()-1);
											
											y = opQ.get(opQ.size()-1);
											opQ.remove(opQ.size()-1);
											x = opQ.get(opQ.size()-1);
											opQ.remove(opQ.size()-1);
											
											result = calculator.subtract(x,y);
											opQ.add(result);
										}
										opStack.add(parts[i]);
									
									}
									else if(opStack.get(opStack.size()-1).equals("-")) {
										opStack.remove(opStack.size()-1);
										
										opStack.add(parts[i]);
										
										float y = opQ.get(opQ.size()-1);
										opQ.remove(opQ.size()-1);
										float x = opQ.get(opQ.size()-1);
										opQ.remove(opQ.size()-1);
										
										result = calculator.subtract(x,y);
										opQ.add(result);
										
										
									} else {
									opStack.add(parts[i]);
								}
							}
						}
						
						continue;
					}
				else {
					currentValue=(float)0;
					return Float.MIN_VALUE;
				}
					
			}
			
			for(int j=0; j<opQ.size()+1;j++) {
				while(!opStack.isEmpty()) {
					String operator = opStack.get(opStack.size()-1);
				
					
					opStack.remove(opStack.size()-1);
					
					
					float y = opQ.get(opQ.size()-1);
					opQ.remove(opQ.size()-1);
					
					float x = opQ.get(opQ.size()-1);
					opQ.remove(opQ.size()-1);
			
					
					if(operator.equals("+")) {
						result = calculator.add(x,y);
						opQ.add(result);
					
					} else if(operator.equals("-")) {
						result = calculator.subtract(x,y);
						opQ.add(result);
					
					} else if(operator.equals("*")) {
						result = calculator.multiply(x,y);
						opQ.add(result);
					
					} else if(operator.equals("/")) {
						result = calculator.divide(x,y);
						opQ.add(result);
					}
				}
				
				
				
			}
			
			return result;
		}
		
		if(result != Float.MIN_VALUE) {
			
		} else {
			currentValue=(float) 0;
		}
		
		return result;
	}	
	

	
	public float add(float x, float y) {
		return x + y;
	}
	
	public float subtract(float x, float y) {
		return x-y;
	}
	
	public float multiply(float x, float y) {
		return x*y;
	}
	
	public float divide(float x, float y) {
		if(y==0) {
			return Float.MIN_VALUE;
		} else {
			return x/y;
		}
		
	}
	
	public float getMemoryValue() {
		if(memory == null) {
			return 0;
		}else {
			return memory;
		}
		
	}
	
	public void setMemoryValue(float memval) {
		memory = memval;
	}
	
	public void clearMemory() {
		setMemoryValue(0);
	}
	

	public float getCurrentValue() {
		if(currentValue!=Float.MIN_VALUE) {
			return currentValue;
		} else {
			return 0;
		}
	}
	
	public void addToHistory(float lastVal) {
		history.add(lastVal);
	}
	
	public void history() {
		for(int i =0;i<history.size();i++) {
			System.out.print(getHistoryValue(i) + " ");
		}
		System.out.println();
	}
	
	public float getHistoryValue(int index) {
		return history.get(index);
	}
}
