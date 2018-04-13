package com.dcits.shoppingcart.reposity;

import java.util.HashMap;
import java.util.Map;

import com.dcits.shoppingcart.model.ShoppingCart;

public class ShoppingCartCache {
	
	public static Map<String, ShoppingCart> cache = new HashMap<>();
}
