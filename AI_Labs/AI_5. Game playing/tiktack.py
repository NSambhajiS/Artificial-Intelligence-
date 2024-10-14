# Minimax Algorithm for Tic-Tac-Toe
import math

# Function to print the Tic-Tac-Toe board
def print_board(board):
    for row in board:
        print("|".join(row))
        print("-" * 5)

# Function to check for a winner
def check_winner(board):
    # Check rows, columns and diagonals
    for row in board:
        if row[0] == row[1] == row[2] and row[0] != " ":
            return row[0]
    for col in range(3):
        if board[0][col] == board[1][col] == board[2][col] and board[0][col] != " ":
            return board[0][col]
    if board[0][0] == board[1][1] == board[2][2] and board[0][0] != " ":
        return board[0][0]
    if board[0][2] == board[1][1] == board[2][0] and board[0][2] != " ":
        return board[0][2]
    return None

# Function to check for a draw
def is_draw(board):
    for row in board:
        if " " in row:
            return False
    return True

# Minimax algorithm to find the best move
def minimax(board, depth, is_maximizing):
    winner = check_winner(board)
    if winner == "X":  # Player X (AI) wins
        return 1
    elif winner == "O":  # Player O (human) wins
        return -1
    elif is_draw(board):  # Draw
        return 0

    if is_maximizing:
        best_score = -math.inf
        for i in range(3):
            for j in range(3):
                if board[i][j] == " ":
                    board[i][j] = "X"
                    score = minimax(board, depth + 1, False)
                    board[i][j] = " "
                    best_score = max(score, best_score)
        return best_score
    else:
        best_score = math.inf
        for i in range(3):
            for j in range(3):
                if board[i][j] == " ":
                    board[i][j] = "O"
                    score = minimax(board, depth + 1, True)
                    board[i][j] = " "
                    best_score = min(score, best_score)
        return best_score

# Function to find the best move for the AI (Player X)
def find_best_move(board):
    best_move = None
    best_score = -math.inf
    for i in range(3):
        for j in range(3):
            if board[i][j] == " ":
                board[i][j] = "X"
                score = minimax(board, 0, False)
                board[i][j] = " "
                if score > best_score:
                    best_score = score
                    best_move = (i, j)
    return best_move

# Function to play the game
def play_game():
    board = [[" " for _ in range(3)] for _ in range(3)]
    current_player = "O"  # Human starts first

    while True:
        print_board(board)
        winner = check_winner(board)
        if winner:
            print(f"Player {winner} wins!")
            break
        elif is_draw(board):
            print("It's a draw!")
            break

        if current_player == "O":
            row, col = map(int, input("Enter your move (row and column): ").split())
            if board[row][col] == " ":
                board[row][col] = "O"
                current_player = "X"
            else:
                print("Invalid move! Try again.")
        else:
            print("AI is making a move...")
            move = find_best_move(board)
            if move:
                board[move[0]][move[1]] = "X"
                current_player = "O"

play_game()
