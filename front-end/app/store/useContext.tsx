import {
  createContext,
  useContext,
  useState,
  useEffect,
  type ReactNode,
} from "react";
import axios from "axios";
import type { Product } from "./Product";

const API = "http://localhost:8080/";

interface ProductContextType {
  data: Product[];
  item: Product | null;
  setItem: (item: Product | null) => void;
  fetchList: () => Promise<void>;
  addItem: (newItem: Product) => Promise<void>;
  updateItem: (id: number, updatedItem: Product) => Promise<void>;
  deleteItem: (id: number) => Promise<void>;
  viewItem: (id: number) => void;
}

const defaultContext: ProductContextType = {
  data: [],
  item: null,
  setItem: () => {},
  fetchList: async () => {},
  addItem: async () => {},
  updateItem: async () => {},
  deleteItem: async () => {},
  viewItem: () => {},
};

const ProductContext = createContext<ProductContextType>(defaultContext);

export const useProductContext = () => useContext(ProductContext);

interface ProductProviderProps {
  children: ReactNode;
}

export function ProductContextProvider({ children }: ProductProviderProps) {
  const [data, setData] = useState<Product[]>([]);
  const [item, setItem] = useState<Product | null>(null);

  const fetchList = async () => {
    try {
      const response = await axios.get<Product[]>(`${API}api/products`);
      setData(response.data);
    } catch (error) {
      console.error("Fetch list error:", error);
    }
  };

  const addItem = async (newItem: Product) => {
    try {
      const response = await axios.post<Product>(`${API}api/products`, newItem);
      setData((prev) => [...prev, response.data]);
    } catch (error) {
      console.error("Add item error:", error);
      throw error;
    }
  };

  const updateItem = async (id: number, updatedItem: Product) => {
    try {
      const response = await axios.put<Product>(
        `${API}api/products/${id}`,
        updatedItem
      );
      setData((prev) =>
        prev.map((item) => (item.id === id ? response.data : item))
      );
    } catch (error) {
      console.error("Update item error:", error);
      throw error;
    }
  };

  const deleteItem = async (id: number) => {
    try {
      await axios.delete(`${API}api/products/${id}`);
      setData((prev) => prev.filter((item) => item.id !== id));
    } catch (error) {
      console.error("Delete item error:", error);
      throw error;
    }
  };

  const viewItem = (id: number) => {
    const found = data.find((item) => item.id === id);
    setItem(found || null);
  };

  useEffect(() => {
    fetchList();
  }, []);

  const value: ProductContextType = {
    data,
    item,
    setItem,
    fetchList,
    addItem,
    updateItem,
    deleteItem,
    viewItem,
  };

  return (
    <ProductContext.Provider value={value}>{children}</ProductContext.Provider>
  );
}
