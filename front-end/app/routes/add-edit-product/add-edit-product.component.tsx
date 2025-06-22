import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import Swal from "sweetalert2";
import type { Product } from "../../store/Product";
import { useProductContext } from "../../store/useContext";

const AddEditProduct = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = Boolean(id);

  const { data, addItem, updateItem } = useProductContext();

  const [isLoading, setIsLoading] = useState(false);
  const [product, setProduct] = useState<Product | null>(null);

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  useEffect(() => {
    if (isEditMode && data.length > 0) {
      const existing = data.find((p) => String(p.id) === id);
      if (existing) {
        setProduct(existing);
        setName(existing.name);
        setDescription(existing.description || "");
        setPrice(String(existing.price));
        setQuantity(String(existing.quantity));
      }
    }
  }, [id, isEditMode, data]);

  const validate = () => {
    const newErrors: { [key: string]: string } = {};

    if (!name.trim()) {
      newErrors.name = "Name is required";
    } else if (name.length < 10 || name.length > 255) {
      newErrors.name = "Name must be between 10 and 255 characters";
    }

    if (description && (description.length < 10 || description.length > 255)) {
      newErrors.description =
        "Description must be between 10 and 255 characters";
    }

    if (!price) {
      newErrors.price = "Price is required";
    } else if (isNaN(Number(price))) {
      newErrors.price = "Price must be a number";
    }

    if (!quantity) {
      newErrors.quantity = "Quantity is required";
    } else if (!Number.isInteger(Number(quantity))) {
      newErrors.quantity = "Quantity must be an integer";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setIsLoading(true);

    const formData: Product = {
      name,
      description,
      price: parseFloat(price),
      quantity: parseInt(quantity),
    };

    try {
      if (isEditMode && product?.id) {
        await updateItem(product.id, formData);
        Swal.fire({
          icon: "success",
          title: "Product Updated!",
          text: `${name} has been updated.`,
          showConfirmButton: false,
          timer: 1500,
        });
      } else {
        await addItem(formData);
        Swal.fire({
          icon: "success",
          title: "Product Added!",
          text: `${name} has been successfully added.`,
          showConfirmButton: false,
          timer: 1500,
        });
      }

      navigate("/products");
    } catch (error) {
      Swal.fire({
        icon: "error",
        title: "Error",
        text: isEditMode
          ? "Failed to update product."
          : "Failed to add product.",
      });
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-popup">
        <div className="modal-header">
          <h5 className="modal-title">
            {isEditMode ? "Edit Product" : "Add Product"}
          </h5>
          <button
            className="close-button"
            onClick={() => navigate("/products")}
          >
            &times;
          </button>
        </div>

        {isLoading && <div className="modal-spinner">Loading...</div>}

        <form onSubmit={handleSubmit} className="modal-body">
          <div className="form-group">
            <label>
              Name <span className="text-danger">*</span>
            </label>
            <input
              type="text"
              className="form-control"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            {errors.name && (
              <small className="text-danger">{errors.name}</small>
            )}
          </div>

          <div className="form-group">
            <label>
              Price <span className="text-danger">*</span>
            </label>
            <input
              type="number"
              className="form-control"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
            {errors.price && (
              <small className="text-danger">{errors.price}</small>
            )}
          </div>

          <div className="form-group">
            <label>
              Quantity <span className="text-danger">*</span>
            </label>
            <input
              type="number"
              className="form-control"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
            {errors.quantity && (
              <small className="text-danger">{errors.quantity}</small>
            )}
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea
              className="form-control"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
            {errors.description && (
              <small className="text-danger">{errors.description}</small>
            )}
          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-outline-dark"
              onClick={() => navigate("/products")}
            >
              Close
            </button>
            <button type="submit" className="btn btn-info" disabled={isLoading}>
              {isEditMode ? "Update" : "Save"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddEditProduct;
