	<!-- Navigation -->
    <%@page import="com.pivision.user.User"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/PiVisionWeb/user/accountDetails"
                style="text-decoration: none; font-variant: small-caps; color: white; font-size: 30px;">Pi Vision</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            
                <ul class="nav navbar-nav navbar-right">
                   <!--  <li id="account">
                        <a href="/PiVisionWeb/user/accountDetails">Account</a>
                    </li> -->
                    <li id="display">
                        <a href="/PiVisionWeb/display/showAllDisplay">Display</a>
                    </li>
                    <li id="presentation">
                        <a href="/PiVisionWeb/presentation/showAllPresentation">Presentation</a>
                    </li>
                    <li id="schedule">
                        <a href="/PiVisionWeb/schedule/showAllSchedule">Schedule</a>
                    </li>
                    <li>
                    <%User userBean =  (User) request.getSession().getAttribute("userBean"); %>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=userBean.getEmailId() %> <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                        <li>
                            <a href="/PiVisionWeb/user/accountDetails"><i class="fa fa-user" aria-hidden="true"></i> Account Details</a>
                        </li>
						<li>
                            <a href="/PiVisionWeb/user/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a>
                        </li>
                    </ul>
                    </li>
                    
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
            
        </div>
        <!-- /.container -->
    </nav>
    
        
