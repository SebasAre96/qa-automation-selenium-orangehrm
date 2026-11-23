Feature: Flujo completo E2E OrangeHRM Data-Driven

  @regression @data-driven
  Scenario Outline: Login y búsqueda de empleado
    Given el usuario accede a OrangeHRM
    When realiza login con usuario "<usuario>" y password "<password>"
    And navega al modulo PIM
    And busca el empleado "<empleado>"
    Then se muestran resultados

    Examples:
      | usuario | password | empleado |
      | Admin   | admin123 | John     |
      | Admin   | admin123 | Peter    |
