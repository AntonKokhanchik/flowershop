<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <div class="col-5">
                <h1> Log in </h1>
                <form method="post">
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input class="form-control" id="login" name="login" />
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" />
                    </div>
                    <input type="submit" class="btn btn-primary" value="Log in" />
                </form>

                <h2>No account?</h2>
                <a href="/login">Log in</a>
            </div>
        </div>

        <%
            for(int i=0; i<4; i++)
                out.println(i);
        %>
    </body>
</html>