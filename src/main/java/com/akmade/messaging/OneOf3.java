package com.akmade.messaging;

import java.util.function.Function;

import com.akmade.messaging.exception.MessagingException;
import com.google.common.base.Supplier;

public class OneOf3<X, Y, Z> {

	public static final <A,B,C> OneOf3<A,B,C> first(A first){
		return new OneOf3<A,B,C>(first, null, null);
	}
	
	public static final <A,B,C> OneOf3<A,B,C> second(B second){
		return new OneOf3<A,B,C>(null, second, null);
	}
	
	public static final <A,B,C> OneOf3<A,B,C> third(C third){
		return new OneOf3<A,B,C>(null, null, third);
	}
	
	private final X first;
	private final Y second;
	private final Z third;
	
	private OneOf3(X x, Y y, Z z){
		first = x;
		second = y;
		third = z;
	}
	
	public boolean isFirst(){
		return first != null;
	}
	
	public boolean isSecond(){
		return second != null;
	}
	
	public boolean isThird(){
		return third != null;
	}

	public X getFirst() {
		if(!isFirst())
			throw new MessagingException("Can not get first when first is empty");
		return first;
	}

	public Y getSecond() {
		if(!isSecond())
			throw new MessagingException("Can not get second when second is empty");
		return second;
	}

	public Z getThird() {
		if(!isThird())
			throw new MessagingException("Can not get third when third is empty");
		return third;
	}
	
	public X firstOrElseGet(Supplier<X> sup){
		if(!isFirst())
			return sup.get();
		return first;
	}
	
	public Y secondOrElseGet(Supplier<Y> sup){
		if(!isSecond())
			return sup.get();
		return second;
	}
	
	public Z thirdOrElseGet(Supplier<Z> sup){
		if(!isThird())
			return sup.get();
		return third;
	}
	
	public <T> OneOf3<T,Y,Z> computeFirst(Function<X,T> func){
		if(isFirst())
			return first(func.apply(this.getFirst()));
		if(isSecond())
			return second(this.getSecond());
		return third(this.getThird());
	}
	
	public <T> OneOf3<X,T,Z> computeSecond(Function<Y,T> func){
		if(isSecond())
			return second(func.apply(this.getSecond()));
		if(isFirst())
			return first(this.getFirst());
		return third(this.getThird());
	}
	
	public <T> OneOf3<X,Y,T> computeThird(Function<Z,T> func){
		if(isThird())
			return third(func.apply(this.getThird()));
		if(isFirst())
			return first(this.getFirst());
		return second(this.getSecond());
	}
}
