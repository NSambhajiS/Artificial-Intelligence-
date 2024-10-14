
# Sudoku code simple
M = 9  # Size of the Sudoku grid (9x9)

# Function to print the Sudoku puzzle
def puzzle(a):
    for i in range(M):
        for j in range(M):
            print(a[i][j], end=" ")
        print()  # New line after each row

# Check if a number can be placed in a given cell
def solve(grid, row, col, num):
    # Check row and column for the number
    for x in range(9):
        if grid[row][x] == num or grid[x][col] == num:
            return False
    # Check the 3x3 sub-grid
    startRow = row - row % 3
    startCol = col - col % 3
    for i in range(3):
        for j in range(3):
            if grid[i + startRow][j + startCol] == num:
                return False
    return True  # Safe to place the number

# Solve the Sudoku puzzle using backtracking
def Suduko(grid, row, col):
    # Check if we reached the end of the grid
    if (row == M - 1 and col == M):
        return True
    # Move to the next row if at the end of the current row
    if col == M:
        row += 1
        col = 0
    # Skip filled cells
    if grid[row][col] > 0:
        return Suduko(grid, row, col + 1)
    
    # Try numbers 1 to 9
    for num in range(1, M + 1):
        if solve(grid, row, col, num):
            grid[row][col] = num  # Place the number
            if Suduko(grid, row, col + 1):  # Recur for next cells
                return True
        grid[row][col] = 0  # Reset if not successful
    return False  # Trigger backtracking

# Initial Sudoku grid with '0' representing empty cells
grid = [
    [0, 7, 0, 0, 2, 0, 0, 4, 6],
    [0, 6, 0, 0, 0, 0, 8, 9, 0],
    [2, 0, 0, 8, 0, 0, 7, 1, 5],
    [0, 8, 4, 0, 9, 7, 0, 0, 0],
    [7, 1, 0, 0, 0, 0, 0, 5, 9],
    [0, 0, 0, 1, 3, 0, 4, 8, 0],
    [6, 9, 7, 0, 0, 2, 0, 0, 8],
    [0, 5, 8, 0, 0, 0, 0, 6, 0],
    [4, 3, 0, 0, 8, 0, 0, 7, 0]
]

# Attempt to solve the Sudoku puzzle
if (Suduko(grid, 0, 0)):
    puzzle(grid)  # Print the solved puzzle
else:
    print("Solution does not exist :(")  # Indicate no solution found
