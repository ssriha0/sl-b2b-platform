package com.sears.os.vo;


public abstract class ABaseVO extends SerializableBaseVO{
	/**
	* public class Person {
	*   String name;
	*      int age;
	* }
	*   
	*  public String toString() {
	*     return new ToStringBuilder(this).
	*       append("name", name).
	*       append("age", age).
	*       toString();
	*   }
	**/
	public abstract String toString();
}
