using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Collections;
using MySql.Data.MySqlClient;

namespace LibarayManage
{
    
    public partial class LoginForm : Form
    {
        private string userName = "root";    //身份
        private MySqlConnection conn;
        public LoginForm()
        {
            InitializeComponent();
        }
        /*
         * 连接数据库
         */
         public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            this.conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        /*
         * 查询用户类型为admin的所有user，password
         */
         public string selectPasswordByAdminUser(string user)
        {
            string password = null;
            string command = "select password from user where username = '" + user + "' and type = 'admin';";
            MySqlCommand selectCommand = new MySqlCommand(command, conn);
            MySqlDataReader reader = selectCommand.ExecuteReader();
            while (reader.Read())
            {
                password = reader[0].ToString();
            }
            return password;
        }
        private void loginButton_Click(object sender, EventArgs e)
        {
            ConnectMariaDB();
            if(userTypeText.Text == "root")
            {
                if("root".Equals(usernameText.Text) && "root".Equals(passwordText.Text))
                {
                    this.DialogResult = DialogResult.OK;
                }
                conn.Close();
            }
            else
            {
                string password = this.selectPasswordByAdminUser(usernameText.Text);
                if (password != null && password.Equals(passwordText.Text))
                {
                    userName = usernameText.Text;
                    this.DialogResult = DialogResult.OK;
                }
                conn.Close();
            }
        }
        public string getUserName()
        {
            return userName;
        }
    }
}
