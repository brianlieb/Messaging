package com.akmade.messaging;

import java.util.function.Function;
import java.util.function.Supplier;

import com.akmade.messaging.exception.MessagingException;

public class Either<X,Y>{
	
	public static <T,Z> Either<T,Z> left(T t){
		return new Either<T,Z>(t, null);
	}
	
	public static <T,Z> Either<T,Z> right(Z z){
		return new Either<T,Z>(null, z);
	}
	
	private final X left;
	private final Y right;
	
	private Either(X left, Y right){
		this.left = left;
		this.right = right;
	}
	
	public boolean isLeft(){
		return left != null;
	}
	
	public boolean isRight(){
		return right != null;
	}
	
	public X getLeft(){
		if(!isLeft())
			throw new MessagingException ("Cannot get left of Either that is right. Use leftOrElseGet");
		return left;
	}
	
	public Y getRight(){
		if(!isRight())
			throw new MessagingException ("Cannot get right of Either that is left. Use rightOrElseGet");
		return right;
	}
	
	public X leftOrElseGet(Supplier<X> sup){
		if(!isLeft())
			return sup.get();
		return left;
	}
	
	public Y rightOrElseGet(Supplier<Y> sup){
		if(!isRight())
			return sup.get();
		return right;
	}
	
	public <Z> Either<Z,Y> computeLeft(Function<X, Z> action){
		if(isLeft())
			return Either.left(action.apply(left));
		return Either.right(this.getRight());
	}
	
}