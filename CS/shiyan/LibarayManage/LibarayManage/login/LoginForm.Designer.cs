namespace LibarayManage
{
    partial class LoginForm
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.username = new System.Windows.Forms.Label();
            this.password = new System.Windows.Forms.Label();
            this.userType = new System.Windows.Forms.Label();
            this.loginButton = new System.Windows.Forms.Button();
            this.passwordText = new System.Windows.Forms.TextBox();
            this.usernameText = new System.Windows.Forms.TextBox();
            this.userTypeText = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // username
            // 
            this.username.AutoSize = true;
            this.username.Location = new System.Drawing.Point(173, 104);
            this.username.Name = "username";
            this.username.Size = new System.Drawing.Size(53, 12);
            this.username.TabIndex = 0;
            this.username.Text = "Username";
            // 
            // password
            // 
            this.password.AutoSize = true;
            this.password.Location = new System.Drawing.Point(173, 170);
            this.password.Name = "password";
            this.password.Size = new System.Drawing.Size(53, 12);
            this.password.TabIndex = 1;
            this.password.Text = "Password";
            // 
            // userType
            // 
            this.userType.AutoSize = true;
            this.userType.Location = new System.Drawing.Point(173, 227);
            this.userType.Name = "userType";
            this.userType.Size = new System.Drawing.Size(59, 12);
            this.userType.TabIndex = 2;
            this.userType.Text = "User Type";
            // 
            // loginButton
            // 
            this.loginButton.Location = new System.Drawing.Point(238, 291);
            this.loginButton.Name = "loginButton";
            this.loginButton.Size = new System.Drawing.Size(75, 23);
            this.loginButton.TabIndex = 3;
            this.loginButton.Text = "LOGIN";
            this.loginButton.UseVisualStyleBackColor = true;
            this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
            // 
            // passwordText
            // 
            this.passwordText.Location = new System.Drawing.Point(274, 161);
            this.passwordText.Name = "passwordText";
            this.passwordText.Size = new System.Drawing.Size(100, 21);
            this.passwordText.TabIndex = 4;
            // 
            // usernameText
            // 
            this.usernameText.Location = new System.Drawing.Point(274, 101);
            this.usernameText.Name = "usernameText";
            this.usernameText.Size = new System.Drawing.Size(100, 21);
            this.usernameText.TabIndex = 5;
            // 
            // userTypeText
            // 
            this.userTypeText.AutoCompleteCustomSource.AddRange(new string[] {
            "root"});
            this.userTypeText.FormattingEnabled = true;
            this.userTypeText.Items.AddRange(new object[] {
            "admin",
            "root"});
            this.userTypeText.Location = new System.Drawing.Point(274, 227);
            this.userTypeText.Name = "userTypeText";
            this.userTypeText.Size = new System.Drawing.Size(100, 20);
            this.userTypeText.TabIndex = 6;
            this.userTypeText.Text = "admin";
            // 
            // LoginForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(574, 373);
            this.Controls.Add(this.userTypeText);
            this.Controls.Add(this.usernameText);
            this.Controls.Add(this.passwordText);
            this.Controls.Add(this.loginButton);
            this.Controls.Add(this.userType);
            this.Controls.Add(this.password);
            this.Controls.Add(this.username);
            this.Name = "LoginForm";
            this.Text = "图书管理系统--登陆界面";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label username;
        private System.Windows.Forms.Label password;
        private System.Windows.Forms.Label userType;
        private System.Windows.Forms.Button loginButton;
        private System.Windows.Forms.TextBox passwordText;
        private System.Windows.Forms.TextBox usernameText;
        private System.Windows.Forms.ComboBox userTypeText;
    }
}

