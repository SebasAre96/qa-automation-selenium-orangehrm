Feature: Flujo completo en OrangeHRM

  @smoke @login
  Scenario: Login exitoso
    Given el usuario accede a OrangeHRM
    When realiza login
    Then visualiza el dashboard

  @regression @login
  Scenario: Login con credenciales invalidas
    Given el usuario accede a OrangeHRM
    When realiza login con usuario "Admin" y password "clave-incorrecta"
    Then se muestra el mensaje de error "Invalid credentials"

  @regression @pim
  Scenario: Buscar empleado
    Given el usuario accede a OrangeHRM
    And realiza login
    When navega al modulo PIM
    And busca el empleado "John"
    Then se muestran resultados

  @regression @e2e
  Scenario: Flujo completo E2E
    Given el usuario accede a OrangeHRM
    When realiza login
    And navega al modulo PIM
    And busca el empleado "John"
    And cierra sesion
    Then vuelve a la pagina de login
