import { formatDate } from "~/utils/utils";
import { useProductContext } from "../../store/useContext";
import Swal from "sweetalert2";
import { useNavigate } from "react-router";

const ProductList = () => {  
  const navigate = useNavigate();
  const { data, deleteItem } = useProductContext();





  const handleDelete = (id: number | undefined) => {
    if (id != null && id != undefined) {
      Swal.fire({
        icon: 'warning',
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, cancel!',
      }).then((result) => {
        if (result.isConfirmed) {
          deleteItem(id);
          Swal.fire({
            icon: "success",
            title: "Deleted!",
            text: "The product has been deleted successfully.",
            showConfirmButton: false,
            timer: 1500,
          });
        }
      });
    }
  };

  return (
    <div className="container mx-auto p-5">
      {data.length > 0 ? (
        <div className="overflow-hidden bg-white rounded-lg shadow-md">
          {/* Desktop View */}
          <div className="hidden md:block">
            <table className="min-w-full bg-gray-50">
              <thead className="bg-gray-100 text-sm text-left text-gray-700">
                <tr>
                  <th className="px-6 py-4 font-semibold text-center">No.</th>
                  <th className="px-6 py-4 font-semibold text-left">Name</th>
                  <th className="px-6 py-4 font-semibold text-center">Price</th>
                  <th className="px-6 py-4 font-semibold text-center">Quantity</th>
                  <th className="px-6 py-4 font-semibold text-center">Date Creation</th>
                  <th className="px-6 py-4 font-semibold text-center">Actions</th>
                </tr>
              </thead>
              <tbody className="text-sm text-gray-700">
                {data.map((product, index) => (
                  <tr key={product.id} className="border-b hover:bg-gray-50">
                    <td className="px-6 py-4 text-center">{product?.id}</td>
                    <td className="px-6 py-4 text-left">{product?.name}</td>
                    <td className="px-6 py-4 text-center">${product?.price}</td>
                    <td className="px-6 py-4 text-center">{product?.quantity}</td>
                    <td className="px-6 py-4 text-center">
                      {formatDate(product?.dateCreation)}
                    </td>

                    <td className="px-6 py-4 text-center flex justify-center space-x-2" style={{ borderBottom: "1px solid #dedede !important" }}>


              
                    <svg onClick={() => navigate(`/products/${product.id}/edit`)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                      <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                    </svg>


                      <svg onClick={() => handleDelete(product?.id)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                      </svg>


                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Mobile View */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 p-4 md:hidden">
            {data.map((product) => (
              <div
                key={product.id}
                className="bg-white shadow-sm rounded-lg p-4 space-y-4"
              >
                <div className="flex items-center justify-between text-sm font-semibold text-gray-700">
                  <div className="text-blue-500">{product?.id}</div>
                  <div className="text-gray-600 flex justify-center space-x-2">


                    <svg onClick={() => navigate(`/products/${product.id}/edit`)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                      <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                    </svg>



                    <svg onClick={() => handleDelete(product?.id)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                      <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                    </svg>

                  </div>
                </div>

                <div className="text-sm text-gray-500">Name: ${product?.name}</div>

                <div className="text-sm text-gray-500">Price: ${product?.price}</div>
                <div className="text-sm text-gray-500">Quantity: {product?.quantity}</div>
                <div className="text-sm text-gray-500">
                  Date Creation: {formatDate(product?.dateCreation)}
                </div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="text-center p-4 text-blue-800 bg-blue-50 rounded-lg">
          No products found!
        </div>
      )}
    </div>
  );
};

export default ProductList;
