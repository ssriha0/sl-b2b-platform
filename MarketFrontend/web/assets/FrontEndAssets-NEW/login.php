<?php 
include 'templates/header-login.php'; 
?>

<div class="container">
  <div class="row">

    <div class="login-content">

      <h3>Log in to Servicelive, or <a href="http://signup.servicelive.com/index.php?Ref_Code=mobile_login_pg">Sign Up</a></h3>

      <form role="form">
        <!-- Hidden by default, validation messages go here. -->
        <div class="alert alert-danger">
          The information you entered does not match our records. For help, click "Forgot Password" below.
        </div>
        <!-- End Hidden -->

        <div class="form-group">
          <label for="usernameEmail">Username or email</label>
          <input type="text" class="form-control" id="usernameEmail">
        </div>
        <div class="form-group">
          <a class="pull-right" href="http://provider.servicelive.com/MarketFrontend/resetPasswordAction!loadResetPasswordPage.action">Forgot password?</a>
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password">
        </div>
        <div class="checkbox pull-right">
          <label for="remember-me">
            <input id="remember-me" type="checkbox">
            Remember me </label>
        </div>
        <button type="submit" class="btn btn btn-primary">
          Log In
        </button>
      </form>
      <p class="margin-top"><i class="glyphicon glyphicon-question-sign"></i> <small>Need help? Call 1-888-549-0640. If you have an account, no need to re-register.</small></p>
    </div>
    
  </div>
</div>

<?php 
include 'templates/footer.php'; 
?>