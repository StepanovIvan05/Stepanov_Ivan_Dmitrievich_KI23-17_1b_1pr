import java.util.Scanner;
import java.util.Random;

public class MatrixOperationApp {

  private static final Scanner scanner = new Scanner(System.in);
  private static int[][] matrix1; // двумерный массив, хранищий элементы первой матрицы
  private static int[][] matrix2; // двумерный массив, хранищий элементы первой матрицы
  private static int[][] resultMatrix; // двумерный массив, хранищий элементы результирующей матрицы
  private static int matrixSize; // размер матрицы
  private static int determinant; // определитель

  public static void main(String[] args) {

    while (true) {
      System.out.println("Меню:");
      System.out.println("1. Ввод исходных данных");
      System.out.println("2. Генерация случайных таблиц");
      System.out.println("3. Выполнение алгоритма");
      System.out.println("4. Вывод результата");
      System.out.println("5. Завершение работы программы");
      System.out.print("Выберите пункт меню: ");

      // обработка ошибок при вводе
      try {
        int choice = 0;
        if (scanner.hasNextInt()) {
          choice = scanner.nextInt();
        } else {
          scanner.next();
        }

        // меню
        switch (choice) {
          case 1:
            inputMatrixData();
            break;
          case 2:
            generation();
            break;
          case 3:
            if (matrix1 == null || matrix2 == null) {
              System.out.println("Ошибка: исходные данные не введены!");
            } else {
              performAlgorithm();
            }
            break;
          case 4:
            if (resultMatrix == null) {
              System.out.println("Ошибка: результат не был вычислен!");
            } else {
              printResult();
            }
            break;
          case 5:
            System.out.println("Программа завершена.");
            System.exit(0);
          default:
            System.out.println("Некорректный выбор. Попробуйте снова.");
        }
      } catch (CustomException e) {
        System.out.println(e.getMessage());
        matrix1 = matrix2 = resultMatrix = null;
      } catch (Exception e) {
        System.out.println("Ошибка. Используйте целые числа.");
        scanner.next();
        matrix1 = matrix2 = resultMatrix = null;
      }
    }
  }

  // ввод матриц и их размера
  private static void inputMatrixData() throws CustomException {

    System.out.print("Введите размер квадратной матрицы(1-10): ");

    matrix1 = matrix2 = resultMatrix = null;
    matrixSize = scanner.nextInt();
    if (matrixSize < 1 || matrixSize > 10) {
      throw new CustomException("Ошибка: нулевой, отрицательный или большой размер матрицы.");
    }

    matrix1 = new int[matrixSize][matrixSize];
    matrix2 = new int[matrixSize][matrixSize];

    System.out.println("Введите элементы первой матрицы:(-100 - 100)");
    inputMatrix(matrix1);

    System.out.println("Введите элементы второй матрицы:(-100 - 100)");
    inputMatrix(matrix2);
  }

  // ввод матрицы
  private static void inputMatrix(int[][] matrix) throws CustomException {
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        System.out.print("Элемент [" + i + "][" + j + "]: ");
        matrix[i][j] = scanner.nextInt();
        if (matrix[i][j] > 100 || matrix[i][j] < -100) {
          throw new CustomException("Ошибка: модуль значения элемента матрицы слишком большой");
        }
      }
    }
  }

  // метод вычисления суммы
  private static void performAlgorithm() {
    resultMatrix = new int[matrixSize][matrixSize];
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
      }
    }
    determinant = calculateDeterminant(resultMatrix);
    System.out.println("Сумма матриц и определитель вычислены.");
  }

  // метод вычисления определителя
  public static int calculateDeterminant(int[][] matrix) {
    int n = matrix.length;
    if (n == 1) {
      return matrix[0][0];
    }
    if (n == 2) {
      return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    int det = 0;
    for (int j = 0; j < n; j++) {
      det +=
          (int) (matrix[0][j] * Math.pow(-1, j) * calculateDeterminant(getSubMatrix(matrix, 0, j)));
    }
    return det;
  }

  public static int[][] getSubMatrix(int[][] matrix, int excluding_row, int excluding_col) {
    int n = matrix.length;
    int[][] subMatrix = new int[n - 1][n - 1];
    int r = -1;
    for (int i = 0; i < n; i++) {
      if (i == excluding_row) continue;
      r++;
      int c = -1;
      for (int j = 0; j < n; j++) {
        if (j == excluding_col) continue;
        subMatrix[r][++c] = matrix[i][j];
      }
    }
    return subMatrix;
  }

  // метод, выводящий результат
  private static void printResult() {
    System.out.println("Сумма матриц:");
    printMatrix(resultMatrix);
    System.out.println("Определитель равен: " + determinant);
  }

  // метод, выводящий матрицу
  private static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  // метод, генерирующий матрицы и их размер
  private static void generation() {

    resultMatrix = null;
    // Генерация случайного размера матрицы (от 1 до 10)
    Random random = new Random();
    matrixSize = random.nextInt(10) + 1;

    // Создание двух матриц одинакового размера
    matrix1 = new int[matrixSize][matrixSize];
    matrix2 = new int[matrixSize][matrixSize];

    // Заполнение матриц случайными числами
    fillMatrixWithRandomNumbers(matrix1);
    fillMatrixWithRandomNumbers(matrix2);

    // Вывод матриц для проверки
    System.out.println("Первая матрица:");
    printMatrix(matrix1);
    System.out.println("Вторая матрица:");
    printMatrix(matrix2);
  }

  // Метод для заполнения матрицы случайными числами
  private static void fillMatrixWithRandomNumbers(int[][] matrix) {
    Random random = new Random();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        matrix[i][j] = random.nextInt(201) - 100; // случайное число от -100 до 100
      }
    }
  }
}

// класс кастомных ошибок
class CustomException extends Exception {
  public CustomException(String message) {
    super(message);
  }
}
