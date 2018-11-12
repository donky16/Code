using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using LibarayManage.root;

namespace LibarayManage
{
    public partial class RootForm : Form
    {
        private MySqlConnection conn = new MySqlConnection();
        private MySqlCommand command = new MySqlCommand();
        private MySqlDataAdapter dataAdapter = new MySqlDataAdapter();
        private DataSet dataSet = new DataSet();

        public RootForm()
        {
            InitializeComponent();
            ConnectMariaDB();
        }
        /*
         * 连接数据库
         */
        public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            AddAdminForm aaForm = new AddAdminForm();
            aaForm.Show();
        }
        /*
         * 查询所有书籍
         */
        private void button2_Click(object sender, EventArgs e)
        {
            string selectAllBookscommandString = "select * from books;";
            MySqlCommand command = new MySqlCommand(selectAllBookscommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "AllBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "AllBooksTable";
        }
        /*
         * 查询未借出书籍
         */
        private void button3_Click(object sender, EventArgs e)
        {
            string selectRootBookscommandString = "select * from books where holder = 'root';";

            command = new MySqlCommand(selectRootBookscommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "RootBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "RootBooksTable";
        }
        /*
         * 查询已借出书籍
         */
        private void button4_Click(object sender, EventArgs e)
        {
            string selectAdminBookscommandString = "select * from books where holder != 'root';";
            command = new MySqlCommand(selectAdminBookscommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "AdminBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "AdminBooksTable";
        }
        /*
         * 模糊查询
         */
        private void button5_Click(object sender, EventArgs e)
        {
            string searchBooksCommandString = null;
            if (comboBox1.Text == "书籍名称")
            {
                searchBooksCommandString = "select * from books where name like '%" + searchText.Text + "%';";
            }
            else
            {
                searchBooksCommandString = "select * from books where author like '%" + searchText.Text + "%';";
            }
            command = new MySqlCommand(searchBooksCommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "SearchBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "SearchBooksTable";
        }
        /*
         * 更新数据库
         */
        private void button6_Click(object sender, EventArgs e)
        {
            try
            {
                MySqlCommandBuilder builder = new MySqlCommandBuilder(dataAdapter);
                int n = dataAdapter.Update(dataSet, dataSet.Tables[0].TableName);
                MessageBox.Show("成功更新" + n.ToString() + "条数据！");
            }
            catch
            {
                MessageBox.Show("更新失败...");
            }
        }
        /*
         * 增加书籍按钮
         */
        private void button7_Click(object sender, EventArgs e)
        {
            AddBook ad = new AddBook();
            ad.Show();
        }
        /*
         * 删除书籍按钮
         */
        private void button9_Click(object sender, EventArgs e)
        {
            try
            {
                for (int i = 0; i < dataGridView1.SelectedRows.Count; i++)
                {
                    int id = Convert.ToInt32(dataGridView1.SelectedRows[i].Cells[0].Value);
                    dataGridView1.Rows.RemoveAt(i);
                    string deleteBookString = "delete from books where id = '" + id + "';";
                    MySqlCommand command = new MySqlCommand(deleteBookString, conn);
                    command.ExecuteNonQuery();
                    MessageBox.Show("成功删除一条数据！");
                }
            }
            catch
            {
                MessageBox.Show("删除失败！");
            }
        }
        /*
         * 修改书籍信息
         */
        private void button8_Click(object sender, EventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells[0].Value);
                string bookName = (String)dataGridView1.SelectedRows[0].Cells[1].Value;
                string author = (String)dataGridView1.SelectedRows[0].Cells[2].Value;

                UpdateBookForm ubf = new UpdateBookForm(id, bookName, author);
                ubf.Show();
            }
            catch
            {
                MessageBox.Show("更新失败...");
            }
        }
    }
}
