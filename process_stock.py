'''
FIXES ::
1. Syntax & Runtime Errors Fixed
    Changed .add() → .append() (lists don’t have .add()).
    Converted price "99.99" (string) → 99.99 (float).

2. Logical Errors Fixed
    Changed total_value = stock_value → total_value += stock_value to sum all values.
    Checked quantity == 0 instead of stock_value == 0 for out-of-stock detection.

3. Negative Stock Handling
    Treated negative stock (p006) as 0 using max(0, quantity).
    Reason: Negative stock is unrealistic; avoids incorrect total value.

4. Total Value Calculation Issue
    Original code overwrote total in each loop → only last product’s value was kept.
    Fixed by accumulating with +=.
'''


# Product data: (Product ID, Price, Stock Quantity)
products = [
    ("p001", 150.00, 5),
    ("p002", 200.00, 0),
    ("p003", 50.50, 10),
    ("p004", 99.99, 3),  # fixed: price is now float
    ("p005", 300.00, 0),
    ("p006", 75.00, 0)   # fixed: negative stock treated as zero
]

def process_stock(product_list):
    total_value = 0
    out_of_stock_items = []

    for product in product_list:
        price = float(product[1])
        quantity = max(0, product[2])  # ensure no negative quantity
        stock_value = price * quantity
        total_value += stock_value

        # Check for out of stock items
        if quantity == 0:
            out_of_stock_items.append(product[0])

    return total_value, out_of_stock_items

stock_value, low_stock = process_stock(products)

print(f"Total value of all stock: {stock_value}")
print(f"Out of stock products: {low_stock}")



'''
OUTPUT ::

❯ python3 process_stock.py

Total value of all stock: 1554.97
Out of stock products: ['p002', 'p005', 'p006']

'''