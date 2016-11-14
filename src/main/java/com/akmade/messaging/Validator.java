package com.akmade.messaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;


public class Validator<T> {

	public static <Y> Builder<Y> newBuilder() {
		return new Builder<Y>();
	}
	
	private final List<ValidatorItem<T>> tests;
	
	private Validator(List<ValidatorItem<T>> items){
		tests = Collections.unmodifiableList(items);
	}
	
	public Either<T,List<String>> validate(T t){
		return tests.stream()
				.map(i -> i.testIt.apply(t))
				.reduce(Either.left(t), (a,b) -> combine(a, b));
	}
	
	
	
	private Either<T,List<String>> combine(Either<T,List<String>> a, Either<T,List<String>> b, BinaryOperator<T> op){
		if (a.isLeft() && b.isLeft())
			return Either.left(op.apply(a.getLeft(), b.getLeft()));
		if(a.isLeft() && b.isRight())
			return Either.right(b.getRight());
		if(a.isRight() && b.isLeft())
			return Either.right(a.getRight());
		List<String> l = new ArrayList<>();
		l.addAll(a.getRight());
		l.addAll(b.getRight());
		return Either.right(l);
	}
	
	private Either<T,List<String>> combine(Either<T,List<String>> a, Either<T,List<String>> b){
		return combine(a, b, (c,d) -> c);
	}
	
	private static class ValidatorItem<Y> {
		private Predicate<Y> test;
		private String error;
		
		private ValidatorItem(Predicate<Y> t, String error){
			this.test = t;
			this.error = error;
		}
		
		private Function<Y,Either<Y,List<String>>> testIt =
			t -> {
				try{
					return (test.test(t))
						? Either.left(t)
						: Either.right(Arrays.asList(error));
				}catch(Exception e){
					return Either.right(Arrays.asList(error));
				}
			};
	}
	
	public static class Builder<Y>{
		private List<ValidatorItem<Y>> items;
		
		public Builder(){
			items = new ArrayList<>();
		}
		
		public Builder<Y> addValidation(Predicate<Y> pred, String errorMessage){
			items.add(new ValidatorItem<Y>(pred, errorMessage));
			return this;
		}
		
		public Validator<Y> build(){
			return new Validator<Y>(items);
		}
	}
	
	
	
}
